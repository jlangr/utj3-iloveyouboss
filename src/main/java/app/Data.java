package app;

import java.sql.ResultSet;
import java.sql.SQLException;

abstract public class Data<T> {
   protected final TableAccess table = new TableAccess();

   abstract protected T createFromRow(ResultSet results) throws SQLException;

   public T get(int id) {
      T t = table.get(id, this::createFromRow);
      return t;
   }
}
