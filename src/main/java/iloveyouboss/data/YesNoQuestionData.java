package iloveyouboss.data;

import iloveyouboss.utils.CheckedConsumer;
import iloveyouboss.domain.Question;
import iloveyouboss.domain.questions.YesNoQuestion;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class YesNoQuestionData extends QuestionData<YesNoQuestion> {
   private static final String TABLE_NAME = "Question";
   private static final String ID_COLUMN = "id";

   public YesNoQuestionData() {
      super(TABLE_NAME, ID_COLUMN);
   }

   @Override
   public void createIfNotExists() {
      table.createIfNotExists(YesNoQuestion.class, List.of("text"));
   }

   @Override
   protected Question createFromRow(ResultSet results) throws SQLException {
      var id = results.getInt(ID_COLUMN);
      var text = results.getString("text");
      return new YesNoQuestion(id, text);
   }

   @Override
   public int add(Question question) {
      return table.insert(new String[] {"text"},
         convertRowToObject(question));
   }

   @Override
   protected CheckedConsumer<PreparedStatement> convertRowToObject(Question question) {
      return statement -> {
         statement.setString(1, question.text());
         // TODO save for options persistence in other question type
//         statement.setString(2, join(",", question.options()));
      };
   }
}
