package iloveyouboss.database;

import iloveyouboss.functional.CheckedConsumer;
import iloveyouboss.functional.CheckedFunction;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;

public class TableAccess {
   public static final String MSG_SELECT_CREATE_ROW_ERROR = "error retrieving from row in %s";
   public static final String MSG_SELECT_ERROR = "error retrieving from %s";
   public static final String MSG_DELETE_ERROR = "error deleting %s";
   public static final String MSG_INSERT_ERROR = "error inserting into %s";
   public static final String MSG_ALTER_ERROR = "error altering id column in %s";
   public static final String MSG_NO_GENERATED_KEYS = "error retrieving id from statement in %s";

   private final Sql sql;
   private final String idColumn;

   public TableAccess(String tableName, String idColumn) {
      sql = new Sql(tableName);
      this.idColumn = idColumn;
   }

   public void execute(String sql) throws SQLException {
      DB.connection().createStatement().execute(sql);
   }

   public void createIfNotExists(Class<?> dataClass, List<String> columnNames) {
      var sqlText = sql.createIfNotExistsStatement(dataClass, idColumn, columnNames);
      try {
         execute(sqlText);
      }
      catch (SQLException e) {
         throw unchecked(e, MSG_ALTER_ERROR);
      }
   }

   public <T> T get(int id, CheckedFunction<ResultSet, T> createObjectFromRow) {
      try (var connection = DB.connection()) {
         var sqlText = sql.selectByIdStatement(id);
         try (var preparedStatement = connection.prepareStatement(sqlText);
              var resultSet = preparedStatement.executeQuery()) {
            return resultSet.next() ? createObjectFromRow.apply(resultSet) : null;
         }
      } catch (SQLException e) {
         throw unchecked(e, MSG_SELECT_CREATE_ROW_ERROR);
      }
   }

   public <T> List<T> selectAll(CheckedFunction<ResultSet, T> createObjectFromRow) {
      List<T> results = new ArrayList<>();
      try (var connection = DB.connection()) {
         var sqlText = sql.selectAllStatement();
         try (var preparedStatement = connection.prepareStatement(sqlText);
              var resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next())
               try {
                  T result = createObjectFromRow.apply(resultSet);
                  results.add(result);
               } catch (SQLException e) {
                  throw unchecked(e, MSG_SELECT_CREATE_ROW_ERROR);
               }
         }
      }
      catch (SQLException e) {
         throw unchecked(e, MSG_SELECT_ERROR);
      }
      return results;
   }

   public void deleteAll() {
      try (var connection = DB.connection()) {
         var sqlText = sql.deleteStatement();
         try (var preparedStatement = connection.prepareStatement(sqlText)) {
            preparedStatement.executeUpdate();
         }
      } catch (SQLException e) {
         throw unchecked(e, MSG_DELETE_ERROR);
      }
   }

   public int insert(String[] columnNames, CheckedConsumer<PreparedStatement> prepare) {
      try (var connection = DB.connection()) {
         var sqlText = sql.insertStatement(columnNames);
         String[] returnedAttributes = {idColumn};
         try (var preparedStatement = connection.prepareStatement(sqlText, returnedAttributes)) {
            prepare.accept(preparedStatement);
            preparedStatement.executeUpdate();
            return generatedKeys(preparedStatement);
         }
      } catch (SQLException e) {
         throw unchecked(e, MSG_INSERT_ERROR);
      }
   }

   private int generatedKeys(PreparedStatement preparedStatement) throws SQLException {
      try (var results = preparedStatement.getGeneratedKeys()) {
         if (results.next())
            return results.getInt(idColumn);
         throw unchecked(MSG_NO_GENERATED_KEYS);
      }
   }

   private RuntimeException unchecked(String errorMessage) {
      return new RuntimeException(format(errorMessage, sql.tableName()));
   }

   private RuntimeException unchecked(SQLException e, String errorMessage) {
      return new RuntimeException(format(errorMessage, sql.tableName()), e.getCause());
   }
}
