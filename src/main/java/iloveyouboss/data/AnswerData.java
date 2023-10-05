package iloveyouboss.data;

import iloveyouboss.database.TableAccess;
import iloveyouboss.domain.Answer;
import iloveyouboss.utils.CheckedConsumer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class AnswerData {
   private static final String TABLE_NAME = "Answer";
   private static final String ID_COLUMN = "id";
   private final TableAccess table;

   public AnswerData() {
      this.table = new TableAccess(TABLE_NAME, ID_COLUMN);
   }

   // TODO can move into interface as default?
   public List<Answer> getAll() {
      return table.selectAll(this::createFromRow);
   }

   public Answer get(int id) {
      return table.get(id, this::createFromRow);
   }

   public void deleteAll() {
      table.deleteAll();
   }

   public void createIfNotExists() {
      table.createIfNotExists(Answer.class, List.of("criterionId", "text"));
   }

   protected Answer createFromRow(ResultSet results) throws SQLException {
      var id = results.getInt(ID_COLUMN);
      var criterionId = results.getInt("criterionId");
      var text = results.getString("text");
      return new Answer(id, criterionId, text);
   }

   public int add(Answer answer) {
      return table.insert(List.of("criterionId", "text"), setIntoStatement(answer));
   }

   protected CheckedConsumer<PreparedStatement> setIntoStatement(Answer answer) {
      return statement -> {
         statement.setInt(1, answer.criterionId());
         statement.setString(2, answer.text());
      };
   }
}
