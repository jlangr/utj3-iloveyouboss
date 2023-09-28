package iloveyouboss.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DB {
   private static String username = "sa";
   private static String password = "";
   private static String database = "jdbc:h2:~/test";

   public static Connection connection() {
      try {
         return DriverManager.getConnection(database, username, password);
      } catch (SQLException e) {
         throw new RuntimeException("unable to connect to " + database, e.getCause());
      }
   }

   // TODO move into tests
   public static void main(String[] args) throws SQLException {
      var createTableSQL = "CREATE TABLE IF NOT EXISTS Question (" +
         "id INT AUTO_INCREMENT PRIMARY KEY," +
         "text VARCHAR(255) NOT NULL," +
         "options VARCHAR(255) NOT NULL" +
         ")";
      connection().createStatement().execute(createTableSQL);
      System.out.println("Question table created successfully.");
   }

   // TODO shouldn't this be in TableAccess
   public static void execute(String sql) throws SQLException {
      connection().createStatement().execute(sql);
   }
}
