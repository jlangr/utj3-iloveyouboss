package iloveyouboss.criteria;

import iloveyouboss.data.Data;
import iloveyouboss.functional.CheckedConsumer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class CriterionData extends Data<Criterion> {
   private static final String TABLE_NAME = "Criterion";
   private static final String ID_COLUMN = "id";

   public CriterionData() {
      super(TABLE_NAME, ID_COLUMN);
   }

   @Override
   public void createIfNotExists() {
      table.createIfNotExists(Criterion.class, List.of("questionId", "expectedAnswer", "isOptional"));
   }

   @Override
   public int add(Criterion criterion) {
      return table.insert(new String[] {"questionId", "expectedAnswer", "isOptional"},
         convertRowToObject(criterion));
   }

   @Override
   protected Criterion createFromRow(ResultSet results) throws SQLException {
      var id = results.getInt(ID_COLUMN);
      var questionId = results.getInt("questionId");
      var expectedAnswer = results.getString("expectedAnswer");
      var isOptional = results.getBoolean("isOptional");
      return new Criterion(id, questionId, expectedAnswer, isOptional);
   }

   @Override
   protected CheckedConsumer<PreparedStatement> convertRowToObject(Criterion criterion) {
      return statement -> {
         statement.setInt(1, criterion.questionId());
         statement.setString(2, criterion.expectedAnswer());
         statement.setBoolean(3, criterion.isOptional());
      };
   }
}
