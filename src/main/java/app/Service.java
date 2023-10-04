package app;

public class Service {
   private final Data<?> questionData;
   private final CriterionData criterionData;

   public Service(Data<? extends Question> questionData, CriterionData criterionData) {
      this.questionData = questionData;
      this.criterionData = criterionData;
   }

   public Object getQuestion(int criterionId) {
      return questionData.get(criterionId);
   }
}
