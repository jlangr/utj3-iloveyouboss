package iloveyouboss.data;

import iloveyouboss.database.TableAccess;
import iloveyouboss.domain.Question;
import iloveyouboss.domain.questions.ChoiceQuestion;
import iloveyouboss.domain.questions.YesNoQuestion;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static iloveyouboss.utils.StringUtils.fromCSV;
import static iloveyouboss.utils.StringUtils.toCSV;

public class QuestionData {
   private static final String TABLE_NAME = "Question";
   private static final String ID_COLUMN = "id";
   private final TableAccess table;

   public QuestionData() {
      this.table = new TableAccess(TABLE_NAME, ID_COLUMN);
   }

   public void deleteAll() {
      table.deleteAll();
   }

   public void createIfNotExists() {
      table.createIfNotExists(Question.class, List.of("type", "text", "answerOptions"));
   }

   public int add(Question question) {
      // TODO use switch. --enable-preview not working in IDEA?
      if (question.getClass() == YesNoQuestion.class)
            return table.insert(List.of("type", "text"),
               statement -> {
                  statement.setString(1, question.type());
                  statement.setString(2, question.text());
               });

      else
            return table.insert(List.of("type", "text", "answerOptions"), statement -> {
               statement.setString(1, question.type());
               statement.setString(2, question.text());
               statement.setString(3, toCSV(question.answerOptions()));
            });
   }

   public List<Question> getAll() {
      return table.selectAll(this::createFromRow);
   }

   public Question get(int id) {
      return table.get(id, this::createFromRow);
   }

   private Question createFromRow(ResultSet results) throws SQLException {
      var id = results.getInt(ID_COLUMN);
      var text = results.getString("text");
      var type = results.getString("type");
      if (type.equals(YesNoQuestion.class.getSimpleName())) {
         return new YesNoQuestion(id, text);
      }
      var answerOptions = results.getString("answerOptions");
      return new ChoiceQuestion(id, text, fromCSV(answerOptions));
   }
}
