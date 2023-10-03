package iloveyouboss.database;

import iloveyouboss.utils.CheckedConsumer;
import iloveyouboss.utils.CheckedFunction;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;

// TODO slow tests!
public class TableAccess {
   public static final String MSG_SELECT_CREATE_ROW_ERROR = "error retrieving from row in %s";
   public static final String MSG_SELECT_ERROR = "error retrieving from %s";
   public static final String MSG_DELETE_ERROR = "error deleting %s";
   public static final String MSG_INSERT_ERROR = "error inserting into %s";
   public static final String MSG_CREATE_TABLE_ERROR = "error creating table %s";
   public static final String MSG_NO_GENERATED_KEYS = "error retrieving id from statement in %s";

   private final Sql sql;
   private final String idColumn;

   public TableAccess(String tableName, String idColumn) {
      sql = new Sql(tableName);
      this.idColumn = idColumn;
   }

   public void execute(String sql) throws SQLException {
      try (var connection = DB.connection()) {
         connection.createStatement().execute(sql);
      }
   }

   public void createIfNotExists(Class<?> dataClass, List<String> columnNames) {
      var sqlText = sql.createStatement(dataClass, idColumn, columnNames);
      try {
         execute(sqlText);
      }
      catch (SQLException e) {
         e.printStackTrace();
         throw unchecked(e, MSG_CREATE_TABLE_ERROR);
      }
   }

   public <T> T get(int id, CheckedFunction<ResultSet, T> createObjectFromRow) {
      try (var connection = DB.connection()) {
         var sqlText = sql.selectByIdStatement(id);
         try (var statement = connection.prepareStatement(sqlText);
              var results = statement.executeQuery()) {
            return results.next() ? createObjectFromRow.apply(results) : null;
         }
      } catch (SQLException e) {
         throw unchecked(e, MSG_SELECT_CREATE_ROW_ERROR);
      }
   }

   public <T> List<T> selectAll(CheckedFunction<ResultSet, T> createObjectFromRow) {
      List<T> rows = new ArrayList<>();
      try (var connection = DB.connection()) {
         var sqlText = sql.selectAllStatement();
         try (var statement = connection.prepareStatement(sqlText);
              var results = statement.executeQuery()) {
            while (results.next())
               try {
                  rows.add(createObjectFromRow.apply(results));
               } catch (SQLException e) {
                  throw unchecked(e, MSG_SELECT_CREATE_ROW_ERROR);
               }
         }
      }
      catch (SQLException e) {
         throw unchecked(e, MSG_SELECT_ERROR);
      }
      return rows;
   }

   public void deleteAll() {
      try (var connection = DB.connection()) {
         var sqlText = sql.deleteStatement();
         try (var statement = connection.prepareStatement(sqlText)) {
            statement.executeUpdate();
         }
      } catch (SQLException e) {
         throw unchecked(e, MSG_DELETE_ERROR);
      }
   }

   public int insert(String[] columnNames, CheckedConsumer<PreparedStatement> rowPreparer) {
      try (var connection = DB.connection()) {
         var sqlText = sql.insertStatement(columnNames);
         var returnedAttributes = new String[] {idColumn};
         try (var statement = connection.prepareStatement(sqlText, returnedAttributes)) {
            rowPreparer.accept(statement);
            statement.executeUpdate();
            return generatedKeys(statement);
         }
      } catch (SQLException e) {
         throw unchecked(e, MSG_INSERT_ERROR);
      }
   }

   private int generatedKeys(PreparedStatement preparedStatement) throws SQLException {
      try (var results = preparedStatement.getGeneratedKeys()) {
         if (results.next())
            return results.getInt(idColumn);
         throw unchecked(new SQLException(), MSG_NO_GENERATED_KEYS);
      }
   }

   private RuntimeException unchecked(SQLException e, String errorMessage) {
      return new RuntimeException(format(errorMessage, sql.tableName()), e.getCause());
   }
}
