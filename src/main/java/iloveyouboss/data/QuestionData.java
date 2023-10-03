package iloveyouboss.data;

import iloveyouboss.functional.CheckedConsumer;
import iloveyouboss.questions.Question;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

abstract public class QuestionData<T> extends Data<Question> {
   public QuestionData(String tableName, String idColumn) {
      super(tableName, idColumn);
   }

   @Override abstract public void createIfNotExists();
   @Override abstract public int add(Question q);
   @Override abstract protected Question createFromRow(ResultSet r) throws SQLException;
   @Override abstract protected CheckedConsumer<PreparedStatement> convertRowToObject(Question o);
}
