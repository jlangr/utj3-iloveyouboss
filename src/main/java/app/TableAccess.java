package app;

import java.sql.ResultSet;

public class TableAccess {
   public <T> T get(int id, CheckedFunction<ResultSet, T> callback) {
      throw new RuntimeException("getting from live database");
   }
}
