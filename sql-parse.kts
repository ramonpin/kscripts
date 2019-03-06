import java.sql.SQLException

fun parseSQL(sql: String): List<String> {

  tailrec fun parseSQLAux(text: List<Char>, isInsideQuotes: Boolean, idx: Int, commands: MutableList<String>): List<String> {
    val first = if(text.isEmpty()) null else text.first()
    val rest = text.drop(1)
    return when {
    // Escaped quotes inside quotes are preserved
      isInsideQuotes && first == '\'' && !rest.isEmpty() && rest.first() == '\'' -> {
        commands[idx] = commands[idx] + "''"
        parseSQLAux(rest.drop(1), isInsideQuotes, idx, commands)
      }
    // Non escaped quotes flip from quote mode to unquote mode and viceversa
      first == '\'' -> {
        commands[idx] = commands[idx] + "'"
        parseSQLAux(rest, !isInsideQuotes, idx, commands)
      }
    // Inside quotes we preserve any character without interpretation
      isInsideQuotes && first != null -> {
        commands[idx] = commands[idx] + first
        parseSQLAux(rest, isInsideQuotes, idx, commands)
      }
    // Remove SQL comments (-- leading comments only)
      first == '-' && !rest.isEmpty() && rest.first() == '-' -> {
        val restWithoutComment = rest.dropWhile { it != '\n' }
        parseSQLAux(restWithoutComment, isInsideQuotes, idx, commands)
      }
    // Outside quotes a ';' marks the end of a sql command
      first == ';' -> {
        commands.add("")
        if(!isInsideQuotes) {
          parseSQLAux(rest, isInsideQuotes, idx + 1, commands)
        } else {
          throw SQLException("Unbalanced quotes at SQL sentence \"${commands[idx]}\"")
        }
      }
    // At the end of the sentence we got all the commands
      first == null -> {
        if(!isInsideQuotes) {
          commands.filterNot { it.isEmpty() || it.isBlank() }
        } else {
          throw SQLException("Unbalanced quotes at SQL sentence \"${commands[idx]}\"")
        }
      }
    // Else we just keep the char and continue reading
      else -> {
        commands[idx] = commands[idx] + first
        parseSQLAux(rest, isInsideQuotes, idx, commands)
      }
    }
  }

  return parseSQLAux(
      text = sql.toCharArray().toList(), // Transform sql String to List<Char>
      isInsideQuotes = false,            // Start in unquote mode
      idx = 0,                           // First command idx
      commands = mutableListOf(""))      // List prepared to recieve first command
}

//println(parseSQL("""hola 'adios perico"""))
//println(parseSQL("""hola 'ad''ios perico"""))

println(parseSQL(""))
println(parseSQL("""hola adios perico"""))
println(parseSQL("""'hola' 'adios' 'perico'"""))
println(parseSQL("""' hola ' ' adios ' ' perico '"""))
println(parseSQL(""" ' hola '  ' adios '  ' perico ' """))
println(parseSQL(""" 'hola''adios''perico'"""))
println(parseSQL(""" 'hola''adios''perico';"""))
println(parseSQL("""adios = ';' and hola = ';'"""))
println(parseSQL("""adios = ';';and hola = ';'"""))
println(parseSQL("""adios = ';' ; and hola = ';'"""))
println(parseSQL("""adios = ';'; and hola = ';'"""))
println(parseSQL("""adios = ';' ;and hola = ';'"""))
println(parseSQL("""adios = ';' ; and hola = ';';"""))
println(parseSQL("""adios = ';' ; ; ; ;  and hola = ';';"""))

println(parseSQL("""
  SELECT *
    FROM PEPE
   WHERE A = 1
     AND B = 'HOLA'
     AND "C" = 3
     AND "DONDE" = 'HO'';LA';

  SELECT *
    FROM PEPE
   WHERE A = 1
     AND B = 'HOLA'
     AND "C" = 3
     AND "DONDE" = 'HO'';LA';
"""))

println(parseSQL("""
  -- Comment sample with ; in the middle
  -- Another comment sample
  SELECT *
    FROM PEPE
   WHERE A = 1
     AND B = 'HOLA' -- This variable
     AND "C" = 3
     AND "DONDE" = 'HO'';LA';

  SELECT *
    FROM PEPE
   WHERE A = 1
     AND B = 'HOLA'
     AND "C" = 3 -- Another variable
     AND "DONDE" = 'HO'';LA';
"""))
