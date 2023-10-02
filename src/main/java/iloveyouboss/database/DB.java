package iloveyouboss.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static java.lang.String.format;

public class DB {
   private String username = "sa";
   private String password = "";
   private String database = "jdbc:h2:~/test";

   private static final String MSG_CONNECT_ERROR = "unable to connect to %s: %s";

   public DB() {
   }

   public DB(String username, String password, String database) {
      this.username = username;
      this.password = password;
      this.database = database;
   }

   public Connection connection() {
      try {
         return DriverManager.getConnection(database, username, password);
      } catch (SQLException e) {
         var errorMessage = format(MSG_CONNECT_ERROR, database, e.getMessage());
         throw new RuntimeException(errorMessage);
      }
   }
}
