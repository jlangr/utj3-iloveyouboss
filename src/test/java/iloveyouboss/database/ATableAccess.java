package iloveyouboss.database;

import org.junit.jupiter.api.*;

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

   @Nested
   class Get {
      @Test
      void returnsItemById() {
         var id = table.insert(new String[] {"x"}, statement ->
            statement.setString(1, "xValue"));

         var retrieved = table.get(id, results ->
            new TestTableAccess(results.getInt("id"), results.getString("x")));

         assertEquals(new TestTableAccess(id, "xValue"), retrieved);
      }

      @Test
      void returnsNullWhenNotFound() {
         var retrieved = table.get(42, results -> new TestTableAccess(0, ""));

         assertNull(retrieved);
      }

      @Test
      void rethrowsWhenPrepareFails() {
         var retrieved = table.get(99, results ->
            new TestTableAccess(results.getInt("id"), results.getString("x")));

      }
   }
}
