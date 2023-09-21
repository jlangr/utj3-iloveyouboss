package iloveyouboss.database;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AnSql {
   private Sql sql = new Sql();

   @Test
   void constructsDeleteStatement() {
      assertEquals("DELETE FROM MyTable", sql.deleteStatement("MyTable"));
   }

   @Test
   void constructsSelectAllStatement() {
      assertEquals("SELECT * FROM MyTable", sql.selectAllStatement("MyTable"));
   }

   @Test
   void constructsInsertStatement() {
      String[] columnNames = {"a", "b", "c"};
      assertEquals("INSERT INTO MyTable (a, b, c) VALUES (?, ?, ?)",
         sql.insertStatement("MyTable", columnNames));
   }
}