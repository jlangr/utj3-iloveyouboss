package iloveyouboss.data;

import iloveyouboss.database.TableAccess;
import iloveyouboss.utils.CheckedConsumer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

abstract public class Data<T> {
   public static final int NOT_PERSISTED_ID = -1;

   protected final TableAccess table;

   public Data(String tableName, String idColumn) {
      this.table = new TableAccess(tableName, idColumn);
   }

   abstract public void createIfNotExists();

   abstract public int add(T t);

   abstract protected T createFromRow(ResultSet results) throws SQLException;

   public List<T> getAll() {
      return table.selectAll(this::createFromRow);
   }

   public T get(int id) {
      // TODO remove later
      T t = table.get(id, this::createFromRow);
      if (t == null) throw new RuntimeException("NO ENTRY");
      return t;
   }

   public void deleteAll() {
      table.deleteAll();
   }

   protected abstract CheckedConsumer<PreparedStatement> setIntoStatement(T object);
}
