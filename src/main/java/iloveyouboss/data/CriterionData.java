package iloveyouboss.data;

import iloveyouboss.database.TableAccess;
import iloveyouboss.domain.Criterion;
import iloveyouboss.utils.CheckedConsumer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class CriterionData {
   private static final String TABLE_NAME = "Criterion";
   private static final String ID_COLUMN = "id";

   private final TableAccess table;

   public CriterionData() {
      this.table = new TableAccess(TABLE_NAME, ID_COLUMN);
   }

   public List<Criterion> getAll() {
      return table.selectAll(this::createFromRow);
   }

   public Criterion get(int id) {
      return table.get(id, this::createFromRow);
   }

   public void deleteAll() {
      table.deleteAll();
   }

   public void createIfNotExists() {
      table.createIfNotExists(Criterion.class, List.of("questionId", "expectedAnswer", "isOptional"));
   }

   public int add(Criterion criterion) {
      return table.insert(List.of("questionId", "expectedAnswer", "isOptional"),
         setIntoStatement(criterion));
   }

   protected Criterion createFromRow(ResultSet results) throws SQLException {
      var id = results.getInt(ID_COLUMN);
      var questionId = results.getInt("questionId");
      var expectedAnswer = results.getString("expectedAnswer");
      var isOptional = results.getBoolean("isOptional");
      return new Criterion(id, questionId, expectedAnswer, isOptional);
   }

   protected CheckedConsumer<PreparedStatement> setIntoStatement(Criterion criterion) {
      return statement -> {
         statement.setInt(1, criterion.questionId());
         statement.setString(2, criterion.expectedAnswer());
         statement.setBoolean(3, criterion.isOptional());
      };
   }
}
