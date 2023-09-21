package iloveyouboss.questions;

import iloveyouboss.database.TableAccess;
import iloveyouboss.questions.yesno.YesNoQuestion;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static java.lang.String.join;

public class QuestionData {
   public static final String TABLE_NAME = "Question";
   private TableAccess sql = new TableAccess(TABLE_NAME);

   // TODO replace with test
   public static void main(String[] args) {
      var repo = new QuestionData();
      repo.deleteAllQuestions();
      repo.add(new YesNoQuestion(1, "Boo"));
      System.out.println(repo.getAll());
   }

   private YesNoQuestion createFromRow(ResultSet results) throws SQLException {
      var id = results.getInt("id");
      var text = results.getString("text");
      return new YesNoQuestion(id, text);
   }

   // TODO what about the int key?

   public List<Question> getAll() {
      return sql.selectAll(this::createFromRow);
   }

   public int add(Question question) {
      return sql.insert(new String[] {"text", "options"},
         (statement -> {
            statement.setString(1, question.text());
            statement.setString(2, join(",", question.options()));
         }));
   }

   void deleteAllQuestions() {
      sql.deleteAll();
   }
}
