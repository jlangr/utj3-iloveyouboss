package iloveyouboss.database;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AnSql {
   private Sql sql = new Sql();

   @Nested
   class ConstructsCreateStatement {
      @Test
      void constructsWithBasicTypes() {
         record X(int id, String col1, String col2, int col3) {}
         assertEquals("CREATE TABLE IF NOT EXISTS x (" +
               "id INT AUTO_INCREMENT PRIMARY KEY, " +
               "col1 VARCHAR(255) NOT NULL, " +
               "col2 VARCHAR(255) NOT NULL, " +
               "col3 INT))",
            sql.createStatement("x",
               X.class,
               "id",
               List.of("col1", "col2", "col3")));
      }

      @Test
      void throwsOnUnknownColumnType() {
         record X(int id, long bad) {}

         Executable createStatement = () -> sql.createStatement("x", X.class, "id", List.of("bad"));

         var thrown = assertThrows(RuntimeException.class, createStatement);
         assertEquals("unsupported type: long", thrown.getMessage());
      }
   }

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