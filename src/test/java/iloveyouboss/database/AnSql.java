package iloveyouboss.database;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class AnSql {
   static final String TABLE_X = "X";
   Sql sqlForTableX = new Sql(TABLE_X);

   @Nested
   class ConstructsCreateStatement {
      @Test
      void withStringsNullableAndNot() {
         record X(@Nullable String nullable, String notNullable) {}
         var statement = sqlForTableX.createStatement(X.class, "id", List.of("nullable", "notNullable"));

         assertEquals("CREATE TABLE IF NOT EXISTS X (" +
               "id INT AUTO_INCREMENT PRIMARY KEY, " +
               "nullable VARCHAR(255), " +
               "notNullable VARCHAR(255) NOT NULL)", statement);
      }

      @Test
      void withBasicTypes() {
         record X(int id, String col1, int col3, boolean col4, List<String> col5) {}

         var statement = sqlForTableX.createStatement(
            X.class,"id", List.of("col1", "col3", "col4", "col5"));

         assertEquals("CREATE TABLE IF NOT EXISTS X (" +
               "id INT AUTO_INCREMENT PRIMARY KEY, " +
               "col1 VARCHAR(255) NOT NULL, " +
               "col3 INT, " +
               "col4 BOOLEAN, " +
               "col5 VARCHAR(255) NOT NULL)",
            statement);
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
      var columnNames = List.of("a", "b", "c");
      assertEquals("INSERT INTO X (a, b, c) VALUES (?, ?, ?)",
         sqlForTableX.insertStatement(columnNames));
   }

   @Test
   void constructsSelectByIdStatement() {
      assertEquals("SELECT * FROM X WHERE id=10",
         sqlForTableX.selectByIdStatement(10));
   }
}