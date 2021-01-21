@file:DependsOn("org.apache.calcite:calcite-core:1.17.0")
@file:DependsOn("org.codehaus.janino:janino:2.5.15")

import org.apache.calcite.adapter.java.ReflectiveSchema
import org.apache.calcite.jdbc.CalciteConnection
import org.apache.calcite.sql.SqlSelect
import org.apache.calcite.sql.parser.SqlParser
import java.sql.DriverManager
import java.util.*

data class Emp(@JvmField val id: Int, @JvmField val name: String, @JvmField val age: Int, @JvmField val deptId: Int)
data class Dept(@JvmField val id:Int, @JvmField val name: String)

object HrSchema {
  @JvmField val emps: Array<Emp> = arrayOf(
      Emp(1, "Ramón", 46, 1),
      Emp(2, "Jose", 23, 1),
      Emp(3, "Juan", 32, 2)
  )

  @JvmField val depts: Array<Dept> = arrayOf(
      Dept(1, "Arch"),
      Dept(2, "Tech")
  )
}

Class.forName("org.apache.calcite.jdbc.Driver")

val info = Properties()
info.setProperty("lex", "JAVA")

val connection = DriverManager.getConnection("jdbc:calcite:", info)!!
val calciteConnection = connection.unwrap(CalciteConnection::class.java)!!
val rootSchema = calciteConnection.rootSchema
val schema = ReflectiveSchema(HrSchema)
rootSchema.add("hr", schema)

val statement = connection.createStatement()
val query =    
"""select d.id, avg(e.age) as edad_media 
  | from hr.emps as e
  | join hr.depts as d on e.deptId = d.id
  | group by d.id""".trimMargin()

// Query parser
val parser = SqlParser.create(query)!!
val node = parser.parseQuery()!! as SqlSelect
node.selectList.list.forEach(::println)

val join = node.from!!

// Query execution
val resultSet = statement.executeQuery(query)!!
val meta = resultSet.metaData!!

println("Resultados....")
var next = true
while(resultSet.next()) {
  println("------------------------------------------")
  (1..meta.columnCount).forEach { println("${meta.getColumnName(it)}: ${resultSet.getInt(it)}") }
}

resultSet.close()
statement.close()
connection.close()

