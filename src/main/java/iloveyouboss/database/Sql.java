package iloveyouboss.database;

import iloveyouboss.reflection.Reflect;

import java.util.List;

import static java.lang.String.format;
import static java.util.Arrays.stream;
import static java.util.stream.Collectors.joining;

public class Sql {
   public String createStatement(String tableName, Class<?> dataClass, String idField, List<String> additionalColumns) {
      var idColumn = format("%s INT AUTO_INCREMENT PRIMARY KEY", idField);
      var columns = additionalColumns.stream()
         .map(column -> declaration(column, dataClass))
         .collect(joining(", "));
      return format("CREATE TABLE IF NOT EXISTS %s (%s, %s))",
         tableName, idColumn, columns);
   }

   private String declaration(String column, Class<?> dataClass) {
      var type = Reflect.type(column, dataClass);
      if (type == int.class)
         return format("%s INT", column);
      if (type == String.class)
         return format("%s VARCHAR(255) NOT NULL", column);
      throw new RuntimeException("unsupported type: " + type);
   }

   public String insertStatement(String tableName, String[] columnNames) {
      var columns = stream(columnNames).collect(joining(", "));
      var questions = stream(columnNames).map(s -> "?").collect(joining(", "));
      return "INSERT INTO " + tableName + format(" (%s) VALUES (%s)", columns, questions);
   }

   public String deleteStatement(String tableName) {
      return format("DELETE FROM %s", tableName);
   }

   public String selectAllStatement(String tableName) {
      return format("SELECT * FROM %s", tableName);
   }

   public String selectByIdStatement(String tableName, int id) {
      return format("SELECT * FROM %s WHERE %s = %d", tableName, "id", id);
   }
}
