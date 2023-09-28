package iloveyouboss.database;

import org.junit.jupiter.api.Test;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class DBTest {
   @Test
   void execute() throws SQLException {
      DB.execute("create table if not exists x");

      var rows = new TableAccess("x", "").selectAll(x -> null);
      assertEquals(0, rows.size());
   }
}