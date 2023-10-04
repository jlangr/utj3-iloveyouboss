package iloveyouboss;

import iloveyouboss.data.CriterionData;
import iloveyouboss.data.Data;
import iloveyouboss.domain.Question;

public class Service {
   private final Data<? extends Question> questionData;
   private final CriterionData criterionData;

   public Service(Data<? extends Question> questionData, CriterionData criterionData) {
      this.questionData = questionData;
      this.criterionData = criterionData;
   }

   public Question getQuestion(int criterionId) {
      Question question = questionData.get(42);
      if (question == null) System.out.println("NULL question");
      return question;
   }
}
