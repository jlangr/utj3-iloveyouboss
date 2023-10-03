package iloveyouboss.data;

import iloveyouboss.domain.Question;

abstract public class QuestionData<T> extends Data<Question> {
   public QuestionData(String tableName, String idColumn) {
      super(tableName, idColumn);
   }
}
