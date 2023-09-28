package iloveyouboss.questions;

import iloveyouboss.database.TableAccess;
import iloveyouboss.functional.CheckedConsumer;
import iloveyouboss.questions.yesno.YesNoQuestion;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class QuestionData {
   private static final String TABLE_NAME = "Question";
   private static final String ID_COLUMN = "id";
   private TableAccess table = new TableAccess(TABLE_NAME, ID_COLUMN);

   // TODO test?
   public void create() {
      table.create(YesNoQuestion.class, List.of("text"));
   }

   private YesNoQuestion createFromRow(ResultSet results) throws SQLException {
      var id = results.getInt(ID_COLUMN);
      var text = results.getString("text");
      return new YesNoQuestion(id, text);
   }

   public void resetId() {
      table.resetId();
   }

   public List<YesNoQuestion> getAll() {
      return table.selectAll(this::createFromRow);
   }

   public int add(YesNoQuestion question) {
      return table.insert(new String[] {"text"},
         convertRowToQuestion(question));
   }

   private CheckedConsumer<PreparedStatement> convertRowToQuestion(YesNoQuestion question) {
      return statement -> {
         statement.setString(1, question.text());
         // TODO save for options persistence in other question type
//         statement.setString(2, join(",", question.options()));
      };
   }

   void deleteAll() {
      table.deleteAll();
   }

   // TODO test
   public YesNoQuestion get(int id) {
      return table.get(id, this::createFromRow);
   }
}
