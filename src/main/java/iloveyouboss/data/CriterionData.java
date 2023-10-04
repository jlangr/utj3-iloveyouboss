package iloveyouboss.data;

import iloveyouboss.domain.Criterion;
import java.sql.ResultSet;

public class CriterionData extends Data<Criterion> {
   public CriterionData() {
      super("Criterion", "id");
   }

   protected Criterion createFromRow(ResultSet results) {
      return new Criterion(1, 42, "because", false);
   }
}
