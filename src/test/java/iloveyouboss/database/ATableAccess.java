package iloveyouboss.database;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ATableAccess {
   static final String TABLE_NAME = "TestTableAccess";
   public static final String ID_COLUMN = "id";
   private TableAccess table;

   record TestTableAccess(int id, String x) {}

   @BeforeAll
   static void createTable() throws SQLException {
      var sql = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME +
         "(id INT AUTO_INCREMENT PRIMARY KEY" +
         ", x VARCHAR(255))";
      DB.connection().createStatement().execute(sql);
   }

   @AfterAll
   static void dropTable() throws SQLException {
      DB.connection().createStatement().execute(
         "DROP TABLE " + TABLE_NAME);
   }

   @BeforeEach
   void truncateTable() throws SQLException {
      DB.connection().createStatement().execute(
         "TRUNCATE TABLE " + TABLE_NAME);
   }

   @BeforeEach
   void createTableAccess() {
      table = new TableAccess(TABLE_NAME, ID_COLUMN);
      table.resetId();
   }

   @Test
   void selectAllRetrievesInsertedRows() {
      table.insert(new String[] {"x"}, statement ->
         statement.setString(1, "xValue"));

      var objects = table.selectAll(results ->
         new TestTableAccess(results.getInt(ID_COLUMN), results.getString("x")));

      assertEquals(List.of(new TestTableAccess(1, "xValue")), objects);
   }

   @Test
   void selectAllRethrowsUncheckedException() {
      table.insert(new String[] {"x"}, statement ->
         statement.setString(1, "xValue"));

      var thrown = assertThrows(RuntimeException.class, () -> {
         table.selectAll(r -> { throw new SQLException("because"); });
      });
      assertEquals("error retrieving from row in TestTableAccess", thrown.getMessage());
   }

   @Test
   void deleteAllRemovesAllRows() {
      table.insert(new String[] {"x"}, statement ->
         statement.setString(1, "xValue"));

      table.deleteAll();

      var objects = table.selectAll(results ->
         new TestTableAccess(results.getInt("id"), results.getString("x")));
      assertTrue(objects.isEmpty());
   }
}
