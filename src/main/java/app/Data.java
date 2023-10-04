package app;

import java.sql.ResultSet;
import java.sql.SQLException;

abstract public class Data<T> {
   protected final TableAccess table = new TableAccess();

   abstract protected T createFromRow(ResultSet results) throws SQLException;

   // TODO mucking with this can eliminate the problem
   // - inline t
   // - return null
   public T get(int id) {
      System.out.println("we are in get method now");
      T t = table.get(id, this::createFromRow);
      return t;
   }
}
