package iloveyouboss.data;

import iloveyouboss.database.TableAccess;

import java.sql.ResultSet;
import java.sql.SQLException;

abstract public class Data<T> {
   public static final int NOT_PERSISTED_ID = -1;

   protected final TableAccess table;

   public Data(String tableName) {
      this.table = new TableAccess();
   }

   abstract protected T createFromRow(ResultSet results) throws SQLException;

   public T get(int id) {
      T t = table.get(id, this::createFromRow);
      if (t == null) throw new RuntimeException("NO ENTRY");
      return t;
   }
}
