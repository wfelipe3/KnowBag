#---------------------------------------------------------------------
#       Name: setJVMOptions.py
#       Role: Set Bizagi JVM Options
#     Author: Leandro Valdes (lv)
# History
#   date      ver  who  what 
# ----------  ---  ---  ---------------------------------------------
# 10/21/2014  0.1  lv  New: created to simplify deployment tasks
# /usr/lib/was/profiles/standalone/bin/wsadmin.sh -port 8880 -username admin -password adminadmin -f /usr/lib/was/SetBizagiConfigure.py server1 was85hostNode01 /usr/lib/was/bizagi.properties
#---------------------------------------------------------------------

import sys, getopt,ConfigParser, os;
#scriptpath = sys.argv[2]
#sys.path.append(scriptpath)
#import Constants
#import PropertyReader

server = sys.argv[0]
node = sys.argv[1]
PROPERTIES_FILE = sys.argv[2]
# Obtain the "simple" server name
def getServerName(s):
    return AdminConfig.showAttribute(s, 'name')

#Obtain the id server
def getServerId(s) :
	return AdminConfig.getid('/Server:'+s+'/')
	
# Convert a list of items separated by linefeeds into an array
def getListArray(l):
    return l.splitlines()
	
def readProperties(pSection, pName) :
	bizagiProperties = ConfigParser.ConfigParser();
	
	bizagiProperties.readfp(open(PROPERTIES_FILE))
	return bizagiProperties.get(pSection, pName);
	
def readSectionProperties(pSection) :
	bizagiProperties = ConfigParser.ConfigParser();
	
	bizagiProperties.readfp(open(PROPERTIES_FILE))
	list =  bizagiProperties.options(pSection);
	return list

#Create service integration bus destination
#Create service integration bus ConnectionFactory
#Create service integration bus Queue
#Create an activation specification	
def SetBUSConfigure():
	print "\n---------------------------------"
	print "---1. BIZAGI BUS CONFIGURATION---"
	print "---------------------------------\n"
	if findBUS('BizAgiBus')== None:
		serverId = getServerId(server);	
		print "Pirint from Server %s" % server
		print "Pirint from Node %s" % node
		print "Pirint from getServerId %s" % getServerId(server)	
		AdminTask.createSIBus('[-bus BizAgiBus -description [Bus called Bizagi, Created Automatically] -busSecurity false ]')
		save('Create BUS')
		AdminTask.addSIBusMember('[-bus BizAgiBus   -node '+node+' -server '+server+' -fileStore -logSize 100 -minPermanentStoreSize 200 -maxPermanentStoreSize 500 -unlimitedPermanentStoreSize false -minTemporaryStoreSize 200 -maxTemporaryStoreSize 500 -unlimitedTemporaryStoreSize false ]')
		save('Create BUS Member')
		AdminTask.createSIBDestination('[-bus BizAgiBus   -name BizAgiQueue -type Queue -reliability ASSURED_PERSISTENT -description -node '+node+' -server '+server+' ]')
		AdminTask.createSIBDestination('[-bus BizAgiBus   -name BizAgiQueueEvent -type Queue -reliability ASSURED_PERSISTENT -description -node '+node+' -server '+server+' ]')
		
		save('Create service integration bus destination')
		AdminTask.createSIBJMSConnectionFactory(serverId, '[-name AsyncAuditControllerFactory -jndiName AsyncAuditControllerFactory -busName BizAgiBus -type queue]')
		AdminTask.createSIBJMSConnectionFactory(serverId, '[-name AsyncControllerFactory -jndiName AsyncControllerFactory -busName BizAgiBus -type queue]')
		save('Create service integration bus ConnectionFactory')	
		AdminTask.createSIBJMSQueue(serverId, '[-name AsyncAuditController -jndiName jms/AsyncAuditController -busName BizAgiBus -queueName BizAgiQueueEvent ]')
		AdminTask.createSIBJMSQueue(serverId, '[-name AsyncController -jndiName jms/AsyncController -busName BizAgiBus -queueName BizAgiQueue]')	
		save('Create service integration bus Queue')	
		AdminTask.createSIBJMSActivationSpec(serverId, '[-name AsyncAuditControllerActivationSpec -jndiName eis/AsyncAuditControllerActivationSpec  -destinationJndiName jms/AsyncAuditController -busName BizAgiBus -destinationType javax.jms.Queue]')
		AdminTask.createSIBJMSActivationSpec(serverId, '[-name AsyncControllerActivationSpec -jndiName eis/AsyncControllerActivationSpec  -destinationJndiName jms/AsyncController -busName BizAgiBus -destinationType javax.jms.Queue]')
		save('Create an activation specification')
	else:
		#AdminConfig.save('createSqlServerJDBCProvider')
		print " BizAgiBus found, skip the creation step and modified "
	

#----------------------------------------------------------
#
# Search for bus using the provided bus name
#
#----------------------------------------------------------
def findBUS(SIBusName):
	print AdminTask.listSIBuses()
	for bus in AdminTask.listSIBuses().splitlines():
		name = AdminConfig.showAttribute(bus, "name")
		if (name == SIBusName):
			return name
#----------------------------------------------------------
#
# finds a JDBC provider based on a keyword in the name (e.g., 'Oracle', 'SQL Server', etc)
#
#----------------------------------------------------------
def findJDBCProvider(providerName):
	jdbc_providers = AdminConfig.list('JDBCProvider', AdminConfig.getid( '/Cell:' + AdminControl.getCell() + '/'))
	for provider in jdbc_providers.splitlines():
		#print provider
		if(provider.find(providerName) > -1):
			return provider

		 
#----------------------------------------------------------
#
# Create Default "Sql Server JDBC provider" if it not exists in cell scope
# sqlserverJarPath=/usr/lib/was/lib/ext/sqljdbc4.jar
#----------------------------------------------------------
def createSqlServerJDBCProvider(sqlserverJarPath):
	print "\n-----------------------------"
	print "---2. CREATE JDBC PROVIDER---"
	print "-----------------------------\n"
	sqlserver_jdbc_provider = findJDBCProvider('"SQL Server JDBC Driver')
	
	if sqlserver_jdbc_provider == None:
		AdminTask.createJDBCProvider('[-scope Server=' + server + ' -databaseType "SQL Server" -providerType "Microsoft SQL Server JDBC Driver" -implementationType "Connection pool data source" -name "SQL Server JDBC Driver" -description "IBM WebSphere Connect JDBC driver for MS SQL Server." -classpath [' + sqlserverJarPath + ' ] -nativePath "" ]')
		AdminConfig.save()
		sqlserver_jdbc_provider = findJDBCProvider('"SQL Server JDBC Driver')
		print " Sql Server JDBC Provider: ",sqlserver_jdbc_provider, " found, skip the creation step and modified the driver jar to ",sqlserverJarPath,"."
		AdminConfig.modify(sqlserver_jdbc_provider, '[[classpath ' + sqlserverJarPath + '] [implementationClassName "com.microsoft.sqlserver.jdbc.SQLServerConnectionPoolDataSource"] [name "SQL Server JDBC Driver"] [isolatedClassLoader "false"] [nativepath ""] [description "IBM WebSphere Connect JDBC driver for MS SQL Server."]]')
		#AdminConfig.save('createSqlServerJDBCProvider')
	else:
		#AdminConfig.save('createSqlServerJDBCProvider')
		print " Sql Server JDBC Provider: ",sqlserver_jdbc_provider, " found, skip the creation step and modified the driver jar to ",sqlserverJarPath,"."
	
		 
#----------------------------------------------------------
#
# Create Default "Oracle JDBC provider" if it not exists in cell scope
#
#----------------------------------------------------------
def createOracleJDBCProvider(oracleJarPath):
  oracle_jdbc_provider = findJDBCProvider('Oracle')
  if oracle_jdbc_provider == None:
    AdminTask.createJDBCProvider('[-scope Cell=' + AdminControl.getCell() + ' -databaseType Oracle -providerType "Oracle JDBC Driver" -implementationType "Connection pool data source" -name "Oracle JDBC Driver" -description "Oracle JDBC Driver" -classpath [' + oracleJarPath + ' ] -nativePath "" ]')	
    AdminConfig.save()
    oracle_jdbc_provider = findJDBCProvider('Oracle')
    AdminConfig.modify(oracle_jdbc_provider, '[[classpath ' + oracleJarPath + '] [implementationClassName "oracle.jdbc.pool.OracleConnectionPoolDataSource"] [name "Oracle JDBC Driver"] [isolatedClassLoader "false"] [nativepath ""] [description "Oracle JDBC Driver"]]')
    print " Oracle JDBC Provider: ",oracle_jdbc_provider, " not found, so created a default Oracle JDBC Provider with driver jar ",oracleJarPath,"."
    AdminConfig.save()
  else:
    #AdminConfig.modify(oracle_jdbc_provider, '[[classpath ' + oracleJarPath + '] [implementationClassName "oracle.jdbc.pool.OracleConnectionPoolDataSource"] [name "Oracle JDBC Driver"] [isolatedClassLoader "false"] [nativepath ""] [description "Oracle JDBC Driver"]]')
    AdminConfig.save()
    print " Oracle JDBC Provider: ",oracle_jdbc_provider, " found, skip the creation step and modified the driver jar to ",oracleJarPath,"."

#----------------------------------------------------------
#
# Delete Default "Oracle JDBC provider" if it exists in cell scope
#
#----------------------------------------------------------
def deleteOracleJDBCProvider():
  oracle_jdbc_provider = findJDBCProvider('Oracle')
  if not oracle_jdbc_provider == None:
    #AdminConfig.remove(oracle_jdbc_provider)
    AdminConfig.save()
    print " Oracle JDBC Provider: ",oracle_jdbc_provider, " deleted."
    AdminConfig.save()
  else:
    print " Oracle JDBC Provider: ",oracle_jdbc_provider, " not found."
#----------------------------------------------------------
#
# findDatasource "SQL SERVER JDBC provider" if it exists in cell scope
#
#----------------------------------------------------------
def findDatasource(datasourceName):
  datasources = AdminConfig.list('DataSource', AdminConfig.getid( '/Cell:' + AdminControl.getCell() + '/'))
  for datasource in datasources.splitlines():
     if(datasource.find(datasourceName) > -1):
         return datasource
#----------------------------------------------------------
#
# createDataSource "SQL SERVER JDBC provider" if it exists in cell scope
#
#----------------------------------------------------------
def createDataSource():
	#try:
		print "\n---------------------------"
		print "---3. CREATE DATA SOURCE---"
		print "---------------------------\n"
		pSection = 'DataSourceSection'	 
		providerName= readProperties(pSection,'providerName')
		friendlyName= readProperties(pSection,'friendlyName')
		jndiName= readProperties(pSection,'jndiName')
		dbURL= readProperties(pSection,'dbURL')
		j2cName= readProperties(pSection,'j2cName')
		minConnections= readProperties(pSection,'minConnections')
		maxConnections= readProperties(pSection,'maxConnections')
		dbName= readProperties(pSection,'dbName')
		dbPort= readProperties(pSection,'dbPort')
		dbUser= readProperties(pSection,'dbUser')
		dbPassword= readProperties(pSection,'dbPassword')	
		ds = findDatasource(friendlyName)
		
		if ds == None:
			strDSArgs = ""
			if providerName == "Oracle":
				strDSArgs = "[-name " + friendlyName + " -jndiName " + jndiName + " -dataStoreHelperClassName com.ibm.websphere.rsadapter.Oracle11gDataStoreHelper -containerManagedPersistence false -configureResourceProperties [[URL java.lang.String " + dbURL +"]]]"
			elif providerName == "SQL Server JDBC Driver":
				strDSArgs = "[-name " + friendlyName + " -jndiName " + jndiName + " -dataStoreHelperClassName com.ibm.websphere.rsadapter.MicrosoftSQLServerDataStoreHelper -containerManagedPersistence false -configureResourceProperties [[databaseName java.lang.String " + dbName + "] [portNumber java.lang.Integer " + dbPort + "] [serverName java.lang.String " + dbURL + "]]]"
			newds = AdminTask.createDatasource(findJDBCProvider(providerName), strDSArgs)
			AdminConfig.modify(newds, '[[statementCacheSize "200"]]')
			
			
			isolationAttr=[["name", "webSphereDefaultIsolationLevel"], ["value", "2"], ["type", "java.lang.Integer"]]
			newPS=[]
			newPS.append(isolationAttr)
			psAttr = ["propertySet", [["resourceProperties", newPS]]]
			newAttrs= []
			newAttrs.append(psAttr)
			AdminConfig.modify(newds, newAttrs)
			
			connPool = AdminConfig.list('ConnectionPool', newds)
			AdminConfig.modify(connPool, '[[connectionTimeout "120"] [maxConnections "' + maxConnections + '"] [unusedTimeout "1800"] [minConnections "' + minConnections +'"] [purgePolicy "EntirePool"] [agedTimeout "0"] [reapTime "180"]]')
			AdminConfig.save()
			print "",providerName,"DataSource ",friendlyName, " created with > JNDI: ", jndiName," dbURL: ", dbURL," j2cName: ", j2cName," minConnections: ", minConnections," maxConnections: ", maxConnections
		else:
			print "",providerName,"DataSource ",friendlyName, " already exists"
		
		themePropertyList = [
			{"name": "user", "value": dbUser},
			{"name": "password", "value": dbPassword}
		]
 
		# Get the J2EE resource property set:
		ds = findDatasource(friendlyName)
		propSet = AdminConfig.showAttribute(ds, 'propertySet')
		resourceProperties = AdminConfig.list("J2EEResourceProperty", propSet).splitlines()
	 
		# Loop through properties we are adding
		for property in themePropertyList:
			found = 0
			for resourceProperty in resourceProperties:
				if (AdminConfig.showAttribute(resourceProperty, "name") == property["name"]): 
					# Modify if values are different
					if (AdminConfig.showAttribute(resourceProperty, "value") != property["value"]): 
						print("Found and modified: ", property["name"])
						print AdminConfig.modify(resourceProperty, [['value', property["value"]]])
					else:
						print("Found but did not modify: ", property["name"])
					found = 1
					break
			if found == 0:
				# Add to the property set
				print("Creating new property: ", property["name"], " ", property["value"])
				print AdminConfig.create('J2EEResourceProperty', propSet, [["name", property["name"]], ["value", property["value"]]])
		 
		# Save the configuration changes:
		AdminConfig.save()
		
		
	
	
#----------------------------------------------------------
#
# addCustomJVMPropertiesToServer Add JVM Properties To Server Stanalone
#
#----------------------------------------------------------
def addCustomJVMPropertiesToServer(server, property, value):
	attr = [];
	# Locate specified Server and its JVM
	server = getServerId(server)
	jvm = AdminConfig.list('JavaVirtualMachine', server)
	attr.append([['name',property],['value',value]])
    # Look for existing property so we can replace it (by removing it first)
	currentProps = getListArray(AdminConfig.list("Property", jvm))
	for prop in currentProps:
		if property == AdminConfig.showAttribute(prop, "name"):
			print "Removing existing property from Server %s" %getServerName(server)
			AdminConfig.remove(prop)

    # Store new property in 'systemProperties' object
	print "Adding property",property,"to Server %s" % getServerName(server)
	AdminConfig.modify(jvm,[['systemProperties',attr]])

#----------------------------------------------------------
#
# addJVMProcessPropertiesToServer Add JVM Properties To Server Standalone
#
#----------------------------------------------------------	
def addJVMProcessPropertiesToServer(server, property, value):
	server = getServerId(server);
	
	pdefs = AdminConfig.showAttribute(server, 'processDefinitions')[1:-1].split(' ')
	
	for p in pdefs:
		if -1 != p.find("JavaProcessDef"):
			pdef = p
			break
	if pdef: # found Java ProcessDef
		jvm=AdminConfig.showAttribute(pdef, 'jvmEntries')[1:-1].split(' ')[0]
		AdminConfig.modify(jvm, [[property, value]])
	else :
		print 'No process Definition found for server %s to set property % with value %s' %(server, property,value)


#-------------------------------------------------------------------
# Set Bizagi custom JVM Properties
#-------------------------------------------------------------------		
def setJVMOptions() :
	print "\n------------------------"
	print "---4. SET JVM OPTIONS---"
	print "------------------------\n"
	pCustomSection = 'JVMCustomSection'
	pSection = 'JVMSection'
	pCustomList = readSectionProperties(pCustomSection)
	pList = readSectionProperties(pSection)
	
  
	print 'Setting Custom JVM Properties'
	
	for pOption in pCustomList :
		#pOption, pValue = readProperties(pCustomSection,pOption).split(',')
		read  = readProperties(pCustomSection,pOption).split(',')
		pOption = read[0]
		pValue = read[1]
		res = addCustomJVMPropertiesToServer(server, pOption, pValue)
  
	print 'Setting JVM Properties'
	
	for pOption in pList :
		read  = readProperties(pSection,pOption).split(',')
		pOption = read[0]
		pValue = read[1]
		res = addJVMProcessPropertiesToServer(server, pOption, pValue)	  
	AdminConfig.save()	
	
	
#---------------------------------------------------------------------
# Name: SetAdminUser()
# Description: Creation User Administrator Bizagi By StandAlone
#---------------------------------------------------------------------	
def SetAdminUser():
	print "\n------------------------------"
	print "---5. SET BIZAGI ADMIN USER---"
	print "------------------------------\n"
	#print AdminTask.searchUsers (['-principalName', '*domain\\admon*'])
	#if AdminTask.searchUsers (['-principalName', '*domain\\admon*']) == None:
	print AdminTask.createUser ('[-uid domain\\admon -password bizagi -confirmPassword bizagi -cn BizAgi -sn BizAgi]')
	AdminConfig.save()
	#else:
	#	print " domain\\admon found, skip the creation step  "
#---------------------------------------------------------------------
# Name: SetSecurityDomain()
# Description: Creation Security Domain 
#---------------------------------------------------------------------		
def SetSecurityDomain():
	print "\n-----------------------------------"
	print "---6. SET BIZAGI SECURITY DOMAIN---"
	print "-----------------------------------\n"
	print AdminTask.listSecurityDomains('-listDescription true')
	#print AdminTask.deleteSecurityDomain('-securityDomainName BizagiDomain -force true')	
	#AdminConfig.save()		
	#print AdminTask.listSecurityDomains('-listDescription true')
	AdminTask.setGlobalSecurity ('[-enabled true]')
	print 'Enabled set Global Security'
	AdminConfig.save()		
	print AdminTask.copySecurityDomainFromGlobalSecurity('-securityDomainName BizagiDomain')
	print 'Copy Security Domain From Global Security '
	AdminConfig.save()		
	print AdminTask.listResourcesInSecurityDomain('-securityDomainName BizagiDomain')
	AdminTask.mapResourceToSecurityDomain('-securityDomainName BizagiDomain -resourceName Cell='+ AdminControl.getCell() +':Node='+node+':Server='+server+'')
	AdminConfig.save()	
	AdminTask.setAppActiveSecuritySettings('-securityDomainName BizagiDomain -issuePermissionWarning false -enforceFineGrainedJCASecurity false -enforceJava2Security false -appSecurityEnabled true ')
	print 'Set App Active Security Settings'
	AdminConfig.save()
	print AdminTask.configureJAASLoginEntry('[-securityDomainName BizagiDomain -loginType system -loginEntryAlias WEB_INBOUND -loginModules "com.bizagi.security.jaas.BizAgiLoginModule" -authStrategies "REQUIRED"]')
	AdminTask.unconfigureLoginModule('-loginType system -loginEntryAlias WEB_INBOUND -loginModule com.ibm.ws.security.server.lm.ltpaLoginModule')
	AdminTask.unconfigureLoginModule('-loginType system -loginEntryAlias WEB_INBOUND -loginModule com.ibm.ws.security.server.lm.wsMapDefaultInboundLoginModule')
	print 'Configuration JAAS Login Entry'
	AdminConfig.save()
	print AdminTask.configureJAASLoginEntry('[-securityDomainName BizagiDomain -loginType system -loginEntryAlias WEB_INBOUND -loginModules "com.ibm.ws.security.server.lm.ltpaLoginModule" -authStrategies "REQUIRED"]')
	print AdminTask.configureJAASLoginEntry('[-securityDomainName BizagiDomain -loginType system -loginEntryAlias WEB_INBOUND -loginModules "com.ibm.ws.security.server.lm.wsMapDefaultInboundLoginModule" -authStrategies "REQUIRED"]')
	AdminConfig.save()
	print '\n\n\n Successful Configuration!\nPlease Logout and Login \n\n\n'	
#---------------------------------------------------------------------
# Name: save()
# Role: All changes are saved
#---------------------------------------------------------------------		
		
def save(description):
	print 'Saving the changes...'+description
	AdminConfig.save()
	print 'Changes saved successfully.'

#---------------------------------------------------------------------
# Name: exitScript()
# Role: terminate process 
#---------------------------------------------------------------------		
		
def exitScript():
	sys.exit()
	print 'Script terminated.'

def get_config(config):
    return AdminConfig.getid(config)

def create_oracle_jdbc_provider():
    node = get_config('/Cell:was85host01Node01Cell/Node:was85hostNode01/Server:server1')
    n1 = ["name", "OracleBizagi"]
    implcn = ["implementationClassName", "oracle.jdbc.pool.OracleConnectionPoolDataSource"]
    classpath = ["classpath", "/usr/lib/was/lib/ext/ojdbc6-11.1.0.6.0.jar"]
    jdbcattrs = [n1, implcn, classpath]
    AdminConfig.create('JDBCProvider', node, jdbcattrs)


def create_oracle_datasource():
    ds = get_config('/Cell:was85host01Node01Cell/Node:was85hostNode01/Server:server1/JDBCProvider:OracleBizagi')
    name = ["name","bizagiOracle"]
    jndi = ["jndiName", "BizAgiJava"]
    ds_helpclass = ["datasourceHelperClassname", "com.ibm.websphere.rsadapter.Oracle11gDataStoreHelper"]
    ds_attr = [name, jndi, ds_helpclass]
    datasource = AdminConfig.create('DataSource', ds, ds_attr)

    propSet = AdminConfig.create('J2EEResourcePropertySet', datasource, [])
    AdminConfig.create('J2EEResourceProperty', propSet, [["name", "URL"], ["value", "jdbc:oracle:thin:@//172.16.0.68:1522/orcl"]])
    AdminConfig.create('J2EEResourceProperty', propSet, [["name", "user"], ["value", "biz"]])
    AdminConfig.create('J2EEResourceProperty', propSet, [["name", "password"], ["value", "Bizagi2016"]])
    AdminConfig.create('J2EEResourceProperty', propSet, [["name", "webSphereDefaultIsolationLevel"], ["value", "2"]])
    AdminConfig.create('J2EEResourceProperty', propSet, [["name", "portNumber"], ["value", "1522"]])


#---------------------------------------------------------------------
# This is the point at which execution begins
#---------------------------------------------------------------------
if ( __name__ == '__main__' ) :
	cmdName = 'SetBUSConfigure'
	missingParms = '%s : Insufficient parameters provided.\n' %cmdName

  #-------------------------------------------------------------------
  # How many user command line parameters were specified?
  #-------------------------------------------------------------------
	argc = len( sys.argv );    
	if ( argc < 2 ) :                      
		print missingParms % locals();    
		exitScript();
	else :                                 		
		print "\n\n\n---------------------------------------"
		print "BIZAGI INSTALATION SCRIPT (Standalone Enviroment).  V.1.0 LINUX by BizAgi \n\n\n"
		print "example: /usr/lib/was/profiles/standalone/bin/wsadmin.sh -port 8880 -username admin -password adminadmin -f /usr/lib/was/SetBizagiConfigure.py server1 was85hostNode01 /usr/lib/was/bizagi.properties"
		print "--------------------------------------- \n\n\n"
		SetBUSConfigure();
		SetAdminUser();
		SetSecurityDomain();
		create_oracle_jdbc_provider()
		create_oracle_datasource()
		save("save datasource")
		
		
		
		
else :
  print 'Error: this script must be executed, not imported.\n';
  