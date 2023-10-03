package iloveyouboss.data;

import iloveyouboss.domain.questions.ChoiceQuestion;
import iloveyouboss.utils.CheckedConsumer;
import iloveyouboss.utils.StringUtils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static iloveyouboss.utils.StringUtils.*;

public class ChoiceQuestionData extends Data<ChoiceQuestion> {
   private static final String TABLE_NAME = "ChoiceQuestion";
   private static final String ID_COLUMN = "id";

   public ChoiceQuestionData() {
      super(TABLE_NAME, ID_COLUMN);
   }

   @Override
   public void createIfNotExists() {
      table.createIfNotExists(ChoiceQuestion.class, List.of("text", "answerOptions"));
   }

   @Override
   protected ChoiceQuestion createFromRow(ResultSet results) throws SQLException {
      var id = results.getInt(ID_COLUMN);
      var text = results.getString("text");
      var optionsCSV = results.getString("answerOptions");
      return new ChoiceQuestion(id, text, fromCSV(optionsCSV));
   }

   @Override
   public int add(ChoiceQuestion question) {
      return table.insert(new String[] {"text", "answerOptions"},
         setIntoStatement(question));
   }

   @Override
   protected CheckedConsumer<PreparedStatement> setIntoStatement(ChoiceQuestion question) {
      return statement -> {
         statement.setString(1, question.text());
         statement.setString(2, toCSV(question.answerOptions()));
      };
   }
}
