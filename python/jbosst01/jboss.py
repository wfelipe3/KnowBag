"""
JBoss Manager

Usage:
    remote-jboss start [--config CONFIG] [--profiler]
    remote-jboss stop [--config CONFIG]
    remote-jboss deploy [--config CONFIG] --ear EAR
    remote-jboss undeploy [--config CONFIG] --ear EAR
    remote-jboss datasource --jboss-path JBOSS_PATH --name NAME --jndi JNDI --driver-name DRIVER_NAME --connection-url CONNECTION_URL --max-pool-size MAX_POOL_SIZE --min-pool-size MIN_POOL_SIZE
Options:
    --jboss-path JBOSS_PATH             JBoss configuration path
    --config CONFIG                     Execution config
    --profiler                          Start jboss with profiler
    --local-ear LOCAL_EAR               Local Ear path
    --ear EAR                           Ear name
    --name NAME                         Data source name
    --jndi JNDI                         Jndi name
    --driver-name DRIVER_NAME           Driver name
    --connection-url CONNECTION_URL     Connection url
    --max-pool-size MAX_POOL_SIZE       Max pool size
    --min-pool-size MIN_POOL_SIZE       Min pool size
"""

from concurrent.futures import ThreadPoolExecutor
from pexpect import pxssh
from docopt import docopt
import subprocess
import threading
import yaml
import sys

lock = threading.Condition()


class JBoss:
    def __init__(self, path, user, password):
        self.path = path
        self.user = user
        self.password = password


def create_connection(host, user, password):
    c = pxssh.pxssh(timeout=999)
    c.login(host, user, password)
    return c


def execute_remote(connection):
    def execute(command):
        connection.sendline(" ".join(command))
        connection.prompt()
        return connection.before

    return execute


def execute_local(command):
    p = subprocess.Popen(" ".join(command), shell=True, stdout=subprocess.PIPE, stderr=subprocess.PIPE)
    output, err = p.communicate()
    return str(output)


def execute_command(executor, command):
    return executor(command)


def jboss_cli(jboss, command):
    return ["{jboss}/bin/jboss-cli.sh".format(jboss=jboss.path),
            "--connect",
            '--user="{user}"'.format(user=jboss.user),
            '--password="{password}"'.format(password=jboss.password),
            '--command="{command}"'.format(command=command)]


def check_state(jboss):
    return jboss_cli(jboss, "read-attribute server-state")


def start(jboss):
    return ["{jboss}/bin/standalone.sh >/dev/null 2>&1 &".format(jboss=jboss.path)]


def start_with_profiler(jboss):
    return ["{jboss}/bin/standalone_with_yjp.sh >/dev/null 2>&1 &".format(jboss=jboss.path)]


def remote_start(start):
    def fun(jboss):
        return ["setsid {start}".format(start="".join(start(jboss)))]

    return fun


def stop(jboss):
    return jboss_cli(jboss, "shutdown")


def deploy(jboss, ear):
    return jboss_cli(jboss, "deploy {ear}".format(ear=ear))


def undeploy(jboss, ear):
    return jboss_cli(jboss, "undeploy {ear}".format(ear=ear))


def parse_config(config):
    with open(config, 'r') as stream:
        return load(stream)


def load(stream):
    try:
        return yaml.load(stream)
    except yaml.YAMLError as e:
        sys.stderr.write(e)


def jboss_from_config(config):
    return JBoss(path=config["path"], user=config["user"], password=config["password"])


def get_executor(config):
    if config["local"]:
        return execute_local
    else:
        return execute_remote(
            create_connection(config["remote"]["host"], config["remote"]["user"], config["remote"]["password"]))


def get_start(config, profiler):
    if profiler:
        start_cmd = start_with_profiler
    else:
        start_cmd = start

    if config["local"]:
        return start_cmd
    else:
        return remote_start(start_cmd)


def execute_in_parallel(executor_with_command):
    with ThreadPoolExecutor(max_workers=len(executor_with_command)) as worker:
        return [worker.submit(execute_command, executor, command) for executor, command in executor_with_command]


def execute_until(predicate):
    def fun(executor):
        def fun2(command):
            res = executor(command)
            if not predicate(res):
                return fun2(command)
            else:
                return res

        return fun2

    return fun


if __name__ == "__main__":
    args = docopt(__doc__, version="0.1")
    if args["--config"] is None:
        config = load(sys.stdin)
    else:
        config = parse_config(args["--config"])
    jboss = jboss_from_config(config)
    executor = get_executor(config)

    if args["undeploy"]:
        execute_command(executor, undeploy(jboss, args["--ear"]))
    elif args["deploy"]:
        execute_command(executor, deploy(jboss, args["--ear"]))
    elif args["start"]:
        state = execute_command(executor, check_state(jboss))
        if "running" not in str(state):
            lock_until_executor = execute_until(lambda x: "running" in str(x))(get_executor(config))
            execute_in_parallel([(lock_until_executor, check_state(jboss)),
                                 (get_executor(config), get_start(config, args["--profiler"])(jboss))])
    elif args["stop"]:
        execute_command(executor, stop(jboss))

    print(yaml.dump(config))
