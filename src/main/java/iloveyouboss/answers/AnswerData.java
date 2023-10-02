package iloveyouboss.answers;

import iloveyouboss.database.DB;
import iloveyouboss.database.TableAccess;
import iloveyouboss.functional.CheckedConsumer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

// TODO duplication with QuestionData
public class AnswerData {
   private static final String TABLE_NAME = "Answer";
   private static final String ID_COLUMN = "id";
   private TableAccess table = new TableAccess(TABLE_NAME, ID_COLUMN, new DB());

   public void createIfNotExists() {
      table.createIfNotExists(Answer.class, List.of("criterionId", "value"));
   }

   private Answer createFromRow(ResultSet results) throws SQLException {
      var id = results.getInt(ID_COLUMN);
      var criterionId = results.getInt("criterionId");
      var text = results.getString("value");
      return new Answer(id, criterionId, text);
   }

   public List<Answer> getAll() {
      return table.selectAll(this::createFromRow);
   }

   public int add(Answer answer) {
      return table.insert(new String[] {"text"},
         convertRowToQuestion(answer));
   }

   private CheckedConsumer<PreparedStatement> convertRowToQuestion(Answer answer) {
      return statement -> {
         statement.setInt(1, answer.id());
         statement.setInt(2, answer.criterionId());
         statement.setString(3, answer.value());
      };
   }

   void deleteAll() {
      table.deleteAll();
   }

   public Answer get(int id) {
      return table.get(id, this::createFromRow);
   }
}
