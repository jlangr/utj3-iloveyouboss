package app;

import java.sql.ResultSet;

public class CriterionData extends Data<Criterion> {
   public CriterionData() {
      super("Criterion");
   }

   protected Criterion createFromRow(ResultSet results) {
      throw new RuntimeException("should not make production call!");
   }
}
