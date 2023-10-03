package iloveyouboss.database;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AnSql {
   static final String TABLE_X = "X";
   private Sql sqlForTableX = new Sql(TABLE_X);

   @Nested
   class ConstructsCreateStatement {
      @Test
      void withBasicTypes() {
         record X(int id, String col1, String col2, int col3, boolean col4) {}
         assertEquals("CREATE TABLE IF NOT EXISTS X (" +
               "id INT AUTO_INCREMENT PRIMARY KEY, " +
               "col1 VARCHAR(255) NOT NULL, " +
               "col2 VARCHAR(255) NOT NULL, " +
               "col3 INT, " +
               "col4 BOOLEAN)",
            sqlForTableX.createStatement(
               X.class,
               "id",
               List.of("col1", "col2", "col3", "col4")));
      }

      @Test
      void throwsOnUnknownColumnType() {
         record X(int id, long bad) {}

         Executable createStatement = () ->
            sqlForTableX.createStatement(X.class, "id", List.of("bad"));

         var thrown = assertThrows(RuntimeException.class, createStatement);
         assertEquals("unsupported type: long", thrown.getMessage());
      }
   }

   @Test
   void constructsDeleteStatement() {
      assertEquals("DELETE FROM X", sqlForTableX.deleteStatement());
   }

   @Test
   void constructsSelectAllStatement() {
      assertEquals("SELECT * FROM X",
         sqlForTableX.selectAllStatement());
   }

   @Test
   void constructsInsertStatement() {
      String[] columnNames = {"a", "b", "c"};
      assertEquals("INSERT INTO X (a, b, c) VALUES (?, ?, ?)",
         sqlForTableX.insertStatement(columnNames));
   }

   @Test
   void constructsSelectByIdStatement() {
      assertEquals("SELECT * FROM X WHERE id=10",
         sqlForTableX.selectByIdStatement(10));
   }

   @Test
   void constructsResetIdStatement() {
      assertEquals("ALTER TABLE X ALTER COLUMN bozo RESTART WITH 1",
         sqlForTableX.resetIdStatement("bozo"));
   }
}