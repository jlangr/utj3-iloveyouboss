package iloveyouboss.database;

import iloveyouboss.utils.ReflectUtils;

import java.util.List;

import static java.lang.String.format;
import static java.util.Arrays.stream;
import static java.util.stream.Collectors.joining;

public record Sql(String tableName) {
   public String createStatement(Class<?> dataClass, String idField, List<String> additionalColumns) {
      var idColumn = format("%s INT AUTO_INCREMENT PRIMARY KEY", idField);
      var columns = additionalColumns.stream()
         .map(column -> declaration(column, dataClass))
         .collect(joining(", "));
      return format("CREATE TABLE IF NOT EXISTS %s (%s, %s)",
         tableName, idColumn, columns);
   }

   private String declaration(String column, Class<?> dataClass) {
      var type = ReflectUtils.accessorType(column, dataClass);
      if (type == boolean.class)
         return format("%s BOOLEAN", column);
      if (type == int.class)
         return format("%s INT", column);
      if (type == String.class || type == List.class) {
         return format("%s VARCHAR(255)", column) +
            (isNullable(column, dataClass) ? "" : " NOT NULL");
      }
      throw new RuntimeException("unsupported type: " + type);
   }

   private boolean isNullable(String column, Class<?> dataClass) {
      return dataClass.isInterface()
         ? ReflectUtils.methodHasAnnotation(column, dataClass, Nullable.class)
         : ReflectUtils.recordComponentHasAnnotation(column, dataClass, Nullable.class);
   }

   public String insertStatement(String[] columnNames) {
      var columns = String.join(", ", columnNames);
      var questions = stream(columnNames).map(s -> "?").collect(joining(", "));
      return "INSERT INTO " + tableName + format(" (%s) VALUES (%s)", columns, questions);
   }

   public String deleteStatement() {
      return format("DELETE FROM %s", tableName);
   }

   public String selectAllStatement() {
      return format("SELECT * FROM %s", tableName);
   }

   public String selectByIdStatement(int id) {
      return format("SELECT * FROM %s WHERE %s=%d", tableName, "id", id);
   }
}
