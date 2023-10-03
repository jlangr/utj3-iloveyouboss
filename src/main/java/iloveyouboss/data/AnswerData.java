package iloveyouboss.data;

import iloveyouboss.domain.Answer;
import iloveyouboss.utils.CheckedConsumer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class AnswerData extends Data<Answer> {
   private static final String TABLE_NAME = "Answer";
   private static final String ID_COLUMN = "id";

   public AnswerData() {
      super(TABLE_NAME, ID_COLUMN);
   }

   @Override
   public void createIfNotExists() {
      table.createIfNotExists(Answer.class, List.of("criterionId", "text"));
   }

   @Override
   protected Answer createFromRow(ResultSet results) throws SQLException {
      var id = results.getInt(ID_COLUMN);
      var criterionId = results.getInt("criterionId");
      var text = results.getString("text");
      return new Answer(id, criterionId, text);
   }

   @Override
   public int add(Answer answer) {
      return table.insert(new String[] {"criterionId", "text"},
         setIntoStatement(answer));
   }

   @Override
   protected CheckedConsumer<PreparedStatement> setIntoStatement(Answer answer) {
      return statement -> {
         statement.setInt(1, answer.criterionId());
         statement.setString(2, answer.text());
      };
   }
}
