package iloveyouboss.database;

import static java.lang.String.format;
import static java.util.Arrays.stream;
import static java.util.stream.Collectors.joining;

public class Sql {
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
}
