@file:MavenRepository("cloudera","https://repository.cloudera.com/artifactory/cloudera-repos/" )
@file:DependsOn("org.apache.solr:solr-solrj:4.10.3-cdh5.8.2")
@file:DependsOn("commons-logging:commons-logging:1.2")

import org.apache.solr.client.solrj.SolrQuery
import org.apache.solr.client.solrj.impl.CloudSolrServer

fun connect(zks: String) = CloudSolrServer(zks).apply { connect() }

val prepro = "AOTLXPPPBD10001:2181,AOTLXPPPBD10002:2181/solr"
val discovery = "AOTLXPRPBD10020:2181,AOTLXPRPBD10021:2181,AOTLXPRPBD10022:2181/solr"
val connection = connect(discovery).apply { defaultCollection = "mirinda-metrics" }

println()
println("    Checking SolR connectivity on Java ${System.getProperty("java.version")}")
println(" _________________________________________________________________")
println("                                              ")
connection.query(SolrQuery("id:cbb626af9b5ae72d6e956adf58ac3378")).results.map { println(it) }
println()
