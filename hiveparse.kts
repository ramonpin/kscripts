@file:MavenRepository("cloudera", "https://repository.cloudera.com/artifactory/cloudera-repos/")
@file:DependsOn("org.apache.hive:hive-exec:1.1.0-cdh5.8.2")

import org.apache.hadoop.hive.ql.tools.*
import org.apache.hadoop.hive.ql.parse.*

val lep = LineageInfo()
println(lep.getLineageInfo("SELECT PEPE, LUIS FROM TABLA_CHULA"))

val pd = ParseDriver()
println(pd.parse("SELECT PEPE, LUIS FROM TABLA_CHULA"))
