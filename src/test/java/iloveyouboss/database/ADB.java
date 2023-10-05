package iloveyouboss.database;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ADB {
   @BeforeEach
   void createFaultyDb() {
      DB.create("badusername", "badpassword", "jdbc:baddriver:~/bad");
   }

   @AfterEach
   void resetDb() {
      DB.reset();
   }

   @Test
   void rethrowsOnError() {
      var thrown = assertThrows(RuntimeException.class, DB::connection);
      assertTrue(thrown.getMessage().startsWith("unable to connect to "));
   }
}