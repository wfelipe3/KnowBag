class Jvm(object):
    def __init__(self, xms, xmx, params):
        self.xms = xms
        self.xmx = xmx
        self.params = params


class Node(object):
    def __init__(self, server, node):
        self.server = server
        self.node = node


class Datasource(object):
    def __init__(self, type, jar, provider_name, friendly_name, jndi, url, min_connections, max_connections, dbname,
                 dbport, dbuser, dbpassword):
        self.type = type
        self.jar = jar
        self.provider_name = provider_name
        self.friendly_name = friendly_name
        self.jndi = jndi
        self.url = url
        self.min_connections = min_connections
        self.max_connections = max_connections
        self.dbname = dbname
        self.dbport = dbport
        self.dbuser = dbuser
        self.dbpassword = dbpassword


class Websphere(object):
    def __init__(self, path, jvm, node, ds):
        self.path = path
        self.jvm = jvm
        self.node = node
        self.ds = ds


def find_bus(bus_name):
    Websphere.find_bus(bus_name)


def get_server_id(node):
    Websphere.get_server_id(node)


def configure_bus(was):
    def configure():
        if find_bus("BizAgiBus") is None:
            Websphere.create_bus(was)
        else:
            Websphere.get_bus(was)

    return configure


def admin_user(was):
    def configure():
        Websphere.create_user(user="domain\\admon", password="bizagi")

    return configure


was = Websphere()
bus = configure_bus(was)
user = admin_user(was)

bus()
user()
