package app;

import java.sql.ResultSet;

public class YesNoQuestionData extends Data<YesNoQuestion> {
   protected YesNoQuestion createFromRow(ResultSet results) {
      return new YesNoQuestion(42, "why");
   }
}
