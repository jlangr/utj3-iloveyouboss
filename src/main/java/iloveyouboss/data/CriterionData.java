package iloveyouboss.data;

import iloveyouboss.domain.Criterion;
import iloveyouboss.utils.CheckedConsumer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class CriterionData extends Data<Criterion> {

   public CriterionData() {
      super("Criterion", "id");
   }

   @Override
   public void createIfNotExists() {
   }

   @Override
   public int add(Criterion criterion) {
      return 1;
   }

   @Override
   protected Criterion createFromRow(ResultSet results) throws SQLException {
      return new Criterion(1, 42, "because", false);
   }

   @Override
   protected CheckedConsumer<PreparedStatement> setIntoStatement(Criterion criterion) {
      return statement -> {};
   }
}
