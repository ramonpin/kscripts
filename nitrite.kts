@file:DependsOn("org.dizitart:nitrite:3.1.0")
import org.dizitart.no2.Nitrite
import org.dizitart.no2.Document
import org.dizitart.no2.filters.Filters

val db = Nitrite.builder()
    .compressed()
    .filePath("/tmp/test.db")
    .openOrCreate("user", "password");

// Create a Nitrite Collection
val collection = db.getCollection("test");

// create a document to populate data
val doc = Document.createDocument("firstName", "John")
     ?.put("lastName", "Doe")
     ?.put("birthDay", java.util.Date())
     ?.put("fruits", arrayOf("apple", "orange"))
     ?.put("note", "a quick brown fox jump over the lazy dog");

// insert the document
collection.insert(doc);

// update the document
collection.update(Filters.eq("firstName", "John"), Document.createDocument("lastName", "Wick"))

// close
db.close()
