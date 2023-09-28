package iloveyouboss.questions;

import iloveyouboss.database.TableAccess;
import iloveyouboss.functional.CheckedConsumer;
import iloveyouboss.questions.yesno.YesNoQuestion;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static java.lang.String.join;

public class QuestionData {
   public static final String TABLE_NAME = "Question";
   private TableAccess table = new TableAccess(TABLE_NAME);

   // TODO
   public void create() {
      table.create(YesNoQuestion.class, "id", List.of("text"));
   }
//   var createTableSQL = "CREATE TABLE IF NOT EXISTS Question (" +
//      "id INT AUTO_INCREMENT PRIMARY KEY," +
//      "text VARCHAR(255) NOT NULL," +
//      "options VARCHAR(255) NOT NULL" +
//      ")";
//   connection().createStatement().execute(createTableSQL);

   private YesNoQuestion createFromRow(ResultSet results) throws SQLException {
      var id = results.getInt("id");
      var text = results.getString("text");
      return new YesNoQuestion(id, text);
   }

   public void resetId() {
      table.resetId("id");
   }

   public List<Question> getAll() {
      return table.selectAll(this::createFromRow);
   }

   public int add(Question question) {
      return table.insert(new String[] {"text", "options"},
         convertRowToQuestion(question));
   }

   private CheckedConsumer<PreparedStatement> convertRowToQuestion(Question question) {
      return statement -> {
         statement.setString(1, question.text());
         statement.setString(2, join(",", question.options()));
      };
   }

   void deleteAll() {
      table.deleteAll();
   }

   // TODO test
   public Question get(int id) {
      return table.get(id, this::createFromRow);
   }
}
