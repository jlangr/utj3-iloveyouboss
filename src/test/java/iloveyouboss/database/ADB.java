package iloveyouboss.database;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ADB {
   @Test
   void rethrowsOnError() {
      var db = new DB("badusername", "badpassword", "jdbc:baddriver:~/bad");

      var thrown = assertThrows(RuntimeException.class, () -> db.connection());
      assertTrue(thrown.getMessage().startsWith("unable to connect to "));
   }
}