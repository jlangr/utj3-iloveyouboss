package iloveyouboss.database;

import iloveyouboss.utils.CheckedConsumer;
import iloveyouboss.utils.CheckedFunction;

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

   private final Sql sql;

   public TableAccess(String tableName, String idColumn) {
      sql = new Sql(tableName);
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

   private RuntimeException unchecked(SQLException e, String errorMessage) {
      return new RuntimeException(format(errorMessage, sql.tableName()), e.getCause());
   }
}
