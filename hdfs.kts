@file:MavenRepository("cloudera", "https://repository.cloudera.com/artifactory/cloudera-repos/")
@file:DependsOn("org.apache.hadoop:hadoop-common:2.6.0-cdh5.8.2")
@file:DependsOn("org.apache.hadoop:hadoop-hdfs:2.6.0-cdh5.8.2")
import org.apache.hadoop.conf.*
import org.apache.hadoop.fs.*
import org.apache.hadoop.security.UserGroupInformation

// Load configuration from Cloudera default path
val config = Configuration(true)
config.addResource("core-default.xml")
config.addResource("hdfs-default.xml")
config.addResource(Path("/etc/hadoop/conf/core-site.xml"))
config.addResource(Path("/etc/hadoop/conf/hdfs-site.xml"))
config.reloadConfiguration()

// Configure user kerberos auth
UserGroupInformation.setConfiguration(config)
UserGroupInformation.loginUserFromKeytab("oyrdatos@COSMOS.ES.FTGROUP", "/users/oyrdatos/oyrdatos.keytab")

// Get hdfs instance and recursively get all files in /user/data
val hdfs = FileSystem.get(config)!!
val files = hdfs.listFiles(Path("/user/data"), true)!!
while(files.hasNext()) {
  val path = files.next().path
  val fstat = hdfs.getFileStatus(path)
  val blocks = fstat.len / fstat.blockSize + 1
  println("${path.name} : ${path.parent} : $blocks")
}

