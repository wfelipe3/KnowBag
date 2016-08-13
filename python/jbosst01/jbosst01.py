"""
JBoss Manager

Usage:
    remote-jboss start --config CONFIG [--profiler]
    remote-jboss stop --config CONFIG
    remote-jboss deploy --config CONFIG --ear EAR
    remote-jboss undeploy --config CONFIG --ear EAR
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

from pexpect import pxssh
from docopt import docopt
import subprocess
import yaml


class JBoss:
    def __init__(self, path, user, password):
        self.path = path
        self.user = user
        self.password = password


def create_connection(host, user, password):
    print("Connecting to {host} with {user}:{password}".format(host=host, user=user, password=password))
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
    return subprocess.call(" ".join(command), shell=True)


def execute_command(executor, command):
    print("Executing {command}".format(command=" ".join(command)))
    return executor(command)


def jboss_cli(jboss, command):
    return ["{jboss}/bin/jboss-cli.sh".format(jboss=jboss.path),
            "--connect",
            '--user="{user}"'.format(user=jboss.user),
            '--password="{password}"'.format(password=jboss.password),
            '--command="{command}"'.format(command=command)]


def start(jboss):
    return ["{jboss}/bin/standalone.sh".format(jboss=jboss.path)]


def start_with_profiler(jboss):
    return ["{jboss}/bin/standalone_with_yjp.sh".format(jboss=jboss.path)]


def stop(jboss):
    return jboss_cli(jboss, "shutdown")


def deploy(jboss, ear):
    return jboss_cli(jboss, "deploy {ear} --force".format(ear=ear))


def undeploy(jboss, ear):
    return jboss_cli(jboss, "undeploy {ear}".format(ear=ear))


def parse_config(config):
    with open(config, 'r') as stream:
        try:
            return yaml.load(stream)
        except yaml.YAMLError as e:
            print(e)


def jboss_from_config(config):
    return JBoss(path=config["path"], user=config["user"], password=config["password"])


def get_executor(config):
    if config["local"]:
        return execute_local
    else:
        return execute_remote(
            create_connection(config["remote"]["host"], config["remote"]["user"], config["remote"]["password"]))


if __name__ == "__main__":
    args = docopt(__doc__, version="0.1")
    config = parse_config(args["--config"])
    jboss = jboss_from_config(config)
    executor = get_executor(config)

    if args["undeploy"]:
        execute_command(executor, undeploy(jboss, args["--ear"]))
    elif args["deploy"]:
        execute_command(executor, deploy(jboss, args["--ear"]))
    elif args["start"]:
        if args["--profiler"]:
            execute_command(executor, start(jboss))
        else:
            execute_command(executor, start_with_profiler(jboss))
    elif args["stop"]:
        execute_command(executor, stop(jboss))
