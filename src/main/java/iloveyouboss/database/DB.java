package iloveyouboss.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DB {
   private static String username = "sa";
   private static String password = "";
   private static String database = "jdbc:h2:~/test";

   // TODO test if throws?
   public Connection connection() {
      try {
         return DriverManager.getConnection(database, username, password);
      } catch (SQLException e) {
         throw new RuntimeException("unable to connect to " + database, e.getCause());
      }
   }
}
