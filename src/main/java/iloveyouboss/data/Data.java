package iloveyouboss.data;

import iloveyouboss.database.DB;
import iloveyouboss.database.TableAccess;
import iloveyouboss.functional.CheckedConsumer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

abstract public class Data<T> {
   protected final TableAccess table;

   public Data(String tableName, String idColumn) {
      this.table = new TableAccess(tableName, idColumn, new DB());
   }

   abstract public void createIfNotExists();

   abstract public int add(T t);

   abstract protected T createFromRow(ResultSet results) throws SQLException;

   public List<T> getAll() {
      return table.selectAll(this::createFromRow);
   }

   public T get(int id) {
      return table.get(id, this::createFromRow);
   }

   public void deleteAll() {
      table.deleteAll();
   }

   protected abstract CheckedConsumer<PreparedStatement> convertRowToObject(T object);
}
