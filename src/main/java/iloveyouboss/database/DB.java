package iloveyouboss.database;

import org.h2.jdbcx.JdbcConnectionPool;

import java.sql.Connection;
import java.sql.SQLException;

import static java.lang.String.format;

public class DB {
   private static final String MSG_CONNECT_ERROR = "unable to connect to %s: %s";
   private static final String DEFAULT_USERNAME = "sa";
   private static final String DEFAULT_PASSWORD = "";
   private static final String DEFAULT_DATABASE = "jdbc:h2:~/test";

   private static DB soleInstance;

   private final String database;
   private JdbcConnectionPool pool;

   private static DB get() {
      if (soleInstance == null)
         soleInstance = new DB(DEFAULT_USERNAME, DEFAULT_PASSWORD, DEFAULT_DATABASE);
      return soleInstance;
   }

   public static void reset() {
      soleInstance = null;
   }

   static void set(DB db) {
      soleInstance = db;
   }

   public static Connection connection() {
      return get().poolConnection();
   }

   public static void create(String username, String password, String database) {
      soleInstance = new DB(username, password, database);
   }

   DB(String username, String password, String database) {
      this.database = database;
      createPool(username, password, database);
   }

   Connection poolConnection() {
      try {
         return pool.getConnection();
      } catch (SQLException e) {
         var errorMessage = format(MSG_CONNECT_ERROR, get().database, e.getMessage());
         throw new RuntimeException(errorMessage);
      }
   }

   private void createPool(String username, String password, String database) {
      if (pool != null)
         pool.dispose();
      pool = JdbcConnectionPool.create(database, username, password);
   }
}
