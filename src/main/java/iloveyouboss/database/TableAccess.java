package iloveyouboss.database;

import iloveyouboss.functional.CheckedConsumer;
import iloveyouboss.functional.CheckedFunction;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;

public class TableAccess {
   public static final String MSG_SELECT_ROW_ERROR = "error retrieving from row in %s";
   public static final String MSG_SELECT_ERROR = "error retrieving from %s";
   public static final String MSG_DELETE_ERROR = "error deleting %s";
   public static final String MSG_INSERT_ERROR = "error inserting into %s";

   private final String tableName;
   private Sql sql = new Sql();

   public TableAccess(String tableName) {
      this.tableName = tableName;
   }

   public <T> List<T> selectAll(CheckedFunction<ResultSet, T> createObjectFromRow) {
      List<T> results = new ArrayList<>();
      try (var connection = DB.connection()) {
         var query = sql.selectAllStatement(tableName);
         try (var preparedStatement = connection.prepareStatement(query);
              var resultSet = preparedStatement.executeQuery()) {
            while (resultSet.next())
               try {
                  results.add(createObjectFromRow.apply(resultSet));
               } catch (SQLException e) {
                  throw new RuntimeException(
                     format(MSG_SELECT_ROW_ERROR, tableName));
               }
         }
      }
      catch (SQLException e) {
         throw new RuntimeException(format(MSG_SELECT_ERROR, tableName), e.getCause());
      }
      return results;
   }

   public void deleteAll() {
      try (var connection = DB.connection()) {
         var sqlStatement = sql.deleteStatement(tableName);
         try (var preparedStatement = connection.prepareStatement(sqlStatement)) {
            preparedStatement.executeUpdate();
         }
      } catch (SQLException e) {
         throw new RuntimeException(format(MSG_DELETE_ERROR, tableName), e.getCause());
      }
   }

   public int insert(String[] columnNames, CheckedConsumer<PreparedStatement> prepare) {
      try (var connection = DB.connection()) {
         var sqlStatement = sql.insertStatement(columnNames, tableName);
         try (var preparedStatement = connection.prepareStatement(sqlStatement)) {
            prepare.accept(preparedStatement);
            return preparedStatement.executeUpdate();
         }
      } catch (SQLException e) {
         throw new RuntimeException(MSG_INSERT_ERROR + tableName, e.getCause());
      }
   }
}
