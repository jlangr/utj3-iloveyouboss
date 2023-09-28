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

   private final Sql sql;

   public TableAccess(String tableName) {
      sql = new Sql(tableName);
   }

   // TODO test
   public void create(Class<?> dataClass, String idColumn, List<String> columnNames) {
      try {
         var createSql = sql.createStatement(dataClass, idColumn, columnNames);
         System.out.println("CREATE SQL: " + createSql);
         DB.execute(createSql);
      }
      catch (SQLException e) {
         throw unchecked(e, MSG_ALTER_ERROR);
      }
   }

   public void resetId(String columnName) {
      var sqlText = sql.resetIdStatement(columnName);
      System.out.println(sqlText);
      try {
         DB.execute(sqlText);
      }
      catch (SQLException e) {
         throw unchecked(e, MSG_ALTER_ERROR);
      }
   }

   // TODO test
   public <T> T get(int id, CheckedFunction<ResultSet, T> createObjectFromRow) {
      try (var connection = DB.connection()) {
         var query = sql.selectByIdStatement(id);
         System.out.println(query);
         // TODO if not found
         try (var preparedStatement = connection.prepareStatement(query);
              var resultSet = preparedStatement.executeQuery()) {
            return createObjectFromRow.apply(resultSet);
         }
      } catch (SQLException e) {
         throw unchecked(e, MSG_SELECT_CREATE_ROW_ERROR);
      }
   }

   public <T> List<T> selectAll(CheckedFunction<ResultSet, T> createObjectFromRow) {
      List<T> results = new ArrayList<>();
      try (var connection = DB.connection()) {
         var query = sql.selectAllStatement();
         try (var preparedStatement = connection.prepareStatement(query);
              var resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next())
               try {
                  T result = createObjectFromRow.apply(resultSet);
                  results.add(result);
               } catch (SQLException e) {
                  unchecked(e, MSG_SELECT_CREATE_ROW_ERROR);
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
         var sqlStatement = sql.deleteStatement();
         try (var preparedStatement = connection.prepareStatement(sqlStatement)) {
            preparedStatement.executeUpdate();
         }
      } catch (SQLException e) {
         throw unchecked(e, MSG_DELETE_ERROR);
      }
   }

   public int insert(String[] columnNames, CheckedConsumer<PreparedStatement> prepare) {
      try (var connection = DB.connection()) {
         var sqlStatement = sql.insertStatement(columnNames);
         try (var preparedStatement = connection.prepareStatement(sqlStatement)) {
            prepare.accept(preparedStatement);
            return preparedStatement.executeUpdate();
         }
      } catch (SQLException e) {
         throw unchecked(e, MSG_INSERT_ERROR);
      }
   }

   private RuntimeException unchecked(SQLException e, String errorMessage) {
      e.printStackTrace();
      return new RuntimeException(format(errorMessage, sql.tableName()), e.getCause());
   }
}
