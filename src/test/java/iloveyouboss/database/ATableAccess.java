package iloveyouboss.database;

import org.junit.jupiter.api.*;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ATableAccess {
   static final String TABLE_NAME = "TestTableAccess";
   static final String ID_COLUMN = "id";

   TableAccess table;

   record TestTableAccess(int id, String x) {}

   @BeforeAll
   static void createTable() throws SQLException {
      var sql = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME +
         "(id INT AUTO_INCREMENT PRIMARY KEY" +
         ", x VARCHAR(255))";
      try (var connection = DB.connection()) {
         connection.createStatement().execute(sql);
      }
   }

   @AfterAll
   static void dropTable() throws SQLException {
      new TableAccess(TABLE_NAME, ID_COLUMN)
         .execute("DROP TABLE " + TABLE_NAME);
   }

   @BeforeEach
   void truncateTable() throws SQLException {
      new TableAccess(TABLE_NAME, ID_COLUMN)
         .execute("TRUNCATE TABLE " + TABLE_NAME);
   }

   @BeforeEach
   void createTableAccess() {
      table = new TableAccess(TABLE_NAME, ID_COLUMN);
   }

   @Test
   void execute() throws SQLException {
      table.execute("create table if not exists x");

      var rows = new TableAccess("x", "").selectAll(x -> null);
      assertEquals(0, rows.size());
   }

   @Test
   void selectAllRetrievesInsertedRows() {
      var id = table.insert(new String[] {"x"}, statement ->
         statement.setString(1, "xValue"));

      var objects = table.selectAll(results ->
         new TestTableAccess(results.getInt(ID_COLUMN), results.getString("x")));

      assertEquals(List.of(new TestTableAccess(id, "xValue")), objects);
   }

   @Test
   void selectAllRethrowsUncheckedException() {
      table.insert(new String[] {"x"}, statement ->
         statement.setString(1, "xValue"));

      var thrown = assertThrows(RuntimeException.class, () ->
         table.selectAll(r -> { throw new SQLException("because"); }));
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

   @Nested
   class Get {
      @Test
      void returnsItemById() {
         var id = insertRowWithXValue("xValue");

         var retrieved = table.get(id, results ->
            new TestTableAccess(results.getInt("id"), results.getString("x")));

         assertEquals(new TestTableAccess(id, "xValue"), retrieved);
      }

      private int insertRowWithXValue(String xValue) {
         return table.insert(new String[] {"x"}, statement ->
            statement.setString(1, xValue));
      }

      @Test
      void returnsNullWhenNotFound() {
         var retrieved = table.get(42, results -> new TestTableAccess(0, ""));

         assertNull(retrieved);
      }

      @Test
      void rethrowsWhenCreateObjectFromRowThrows() {
         var id = insertRowWithXValue("1");

         var thrown = assertThrows(RuntimeException.class, () ->
            table.get(id, results -> { throw new SQLException("uh oh"); }));
         assertTrue(thrown.getMessage().contains("error retrieving from row in "));
      }
   }
}
