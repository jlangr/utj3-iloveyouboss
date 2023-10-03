package iloveyouboss.data;

import iloveyouboss.domain.questions.YesNoQuestion;
import iloveyouboss.utils.CheckedConsumer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class YesNoQuestionData extends Data<YesNoQuestion> {
   private static final String TABLE_NAME = "YesNoQuestion";
   private static final String ID_COLUMN = "id";

   public YesNoQuestionData() {
      super(TABLE_NAME, ID_COLUMN);
   }

   @Override
   public void createIfNotExists() {
      table.createIfNotExists(YesNoQuestion.class, List.of("text"));
   }

   @Override
   protected YesNoQuestion createFromRow(ResultSet results) throws SQLException {
      var id = results.getInt(ID_COLUMN);
      var text = results.getString("text");
      return new YesNoQuestion(id, text);
   }

   @Override
   public int add(YesNoQuestion question) {
      return table.insert(new String[] {"text"},
         setIntoStatement(question));
   }

   @Override
   protected CheckedConsumer<PreparedStatement> setIntoStatement(YesNoQuestion question) {
      return statement -> statement.setString(1, question.text());
   }
}
