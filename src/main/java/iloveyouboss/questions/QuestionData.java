package iloveyouboss.questions;

import iloveyouboss.database.DB;
import iloveyouboss.questions.yesno.YesNoQuestion;

import java.sql.SQLException;
import java.time.Clock;
import java.util.ArrayList;
import java.util.List;

public class QuestionData {
   public static void main(String[] args) {
      var repo = new QuestionData();
      repo.deleteAllQuestions();
      repo.add(new YesNoQuestion(1, "Boo"));
      System.out.println(repo.getAll());
   }

   private Clock clock = Clock.systemUTC();

   void setClock(Clock clock) {
      this.clock = clock;
   }

   // TODO what about the int key?

   public List<Question> getAll() {
      List<Question> questions = new ArrayList<>();

      try (var connection = DB.connection()) {
         var query = "SELECT * FROM Question";
         try (var preparedStatement = connection.prepareStatement(query);
              var resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
               var id = resultSet.getInt("id");
               var text = resultSet.getString("text");
               questions.add(new YesNoQuestion(id, text));
            }
         }
      }
      catch (SQLException e) {
         throw new RuntimeException("error retrieving questions", e.getCause());
      }
      return questions;
   }

   public int add(Question question) {
      try (var connection = DB.connection()) {
         var sql = "INSERT INTO Question (text, options) VALUES (?, ?)";
         try (var preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, question.text());
            preparedStatement.setString(2, String.join(",", question.options()));
            return preparedStatement.executeUpdate();
         }
      } catch (SQLException e) {
         throw new RuntimeException("error inserting question " + question, e.getCause());
      }
   }

   void deleteAllQuestions() {
      try (var connection = DB.connection()) {
         String deleteSQL = "DELETE FROM Question";
         try (var preparedStatement = connection.prepareStatement(deleteSQL)) {
            preparedStatement.executeUpdate();
         }
      } catch (SQLException e) {
         throw new RuntimeException("error deleting question", e.getCause());
      }
   }
}
