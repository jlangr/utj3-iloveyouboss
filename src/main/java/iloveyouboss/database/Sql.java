package iloveyouboss.database;

import static java.lang.String.format;
import static java.util.Arrays.stream;
import static java.util.stream.Collectors.joining;

public class Sql {
   public String insertStatement(String[] columnNames, String tableName1) {
      var columns = stream(columnNames).collect(joining(", "));
      var questions = stream(columnNames).map(s -> "?").collect(joining(", "));
      return "INSERT INTO " + tableName1 + format(" (%s) VALUES (%s)", columns, questions);
   }

   public String deleteStatement(String tableName1) {
      return format("DELETE FROM %s", tableName1);
   }

   public String selectAllStatement(String tableName1) {
      return format("SELECT * FROM %s", tableName1);
   }
}
