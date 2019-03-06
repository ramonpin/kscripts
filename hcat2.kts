@file:MavenRepository("cloudera", "https://repository.cloudera.com/artifactory/cloudera-repos/")
@file:DependsOn("org.apache.hadoop:hadoop-client:2.6.0-cdh5.8.2")
@file:DependsOn("org.apache.hive:hive-metastore:1.1.0-cdh5.8.2")

import org.apache.hadoop.conf.*
import org.apache.hadoop.fs.*
import org.apache.hadoop.security.UserGroupInformation
import org.apache.hadoop.hive.metastore.HiveMetaStoreClient
import org.apache.hadoop.hive.conf.HiveConf

// Load configuration from Cloudera default path
val config = Configuration(true)
config.addResource("core-default.xml")
config.addResource("hdfs-default.xml")
config.addResource(Path("/etc/hadoop/conf/core-site.xml"))
config.addResource(Path("/etc/hadoop/conf/hdfs-site.xml"))
config.addResource(Path("/etc/hive/conf/hive-site.xml"))
config.reloadConfiguration()

// Configure user kerberos auth
UserGroupInformation.setConfiguration(config)
UserGroupInformation.loginUserFromKeytab("oyrdatos@COSMOS.ES.FTGROUP", "/etc/mirinda/oyrdatos.keytab")

// Access the HiveMetastore API
var hiveConf = HiveConf(config, HiveConf::class.java)
val metastore = HiveMetaStoreClient(hiveConf)
metastore.getAllTables("raw").forEach { println(it) }
hiveConf.forEach { println(it) }

