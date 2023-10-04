package app;

public class Service {
   private final Data<? extends Question> questionData;
   private final CriterionData criterionData;

   public Service(Data<? extends Question> questionData, CriterionData criterionData) {
      this.questionData = questionData;
      this.criterionData = criterionData;
   }

   public Question getQuestion(int criterionId) {
      return questionData.get(criterionId);
   }
}
