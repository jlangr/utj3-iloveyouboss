package iloveyouboss.data;

import iloveyouboss.domain.questions.YesNoQuestion;
import iloveyouboss.utils.CheckedConsumer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class YesNoQuestionData extends Data<YesNoQuestion> {

   public YesNoQuestionData() {
      super("YesNoQuestion", "id");
   }

   @Override
   public void createIfNotExists() {
   }

   @Override
   protected YesNoQuestion createFromRow(ResultSet results) throws SQLException {
      return new YesNoQuestion(42, "why");
   }

   @Override
   public int add(YesNoQuestion question) {
      return 42;
   }

   @Override
   protected CheckedConsumer<PreparedStatement> setIntoStatement(YesNoQuestion question) {
      return statement -> {};
   }
}
