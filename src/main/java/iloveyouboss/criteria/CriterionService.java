package iloveyouboss.criteria;

import iloveyouboss.InvalidAnswerException;
import iloveyouboss.questions.Question;
import iloveyouboss.questions.YesNoQuestionData;

import static iloveyouboss.questions.Question.AnswerNotProvided;

public class CriterionService {
   private YesNoQuestionData questionData;
   private CriterionData criterionData;

   public CriterionService(YesNoQuestionData questionData, CriterionData criterionData) {
      this.questionData = questionData;
      this.criterionData = criterionData;
   }

   public boolean isMetBy(Criterion criterion, String answer) {
      if (answer.equals(AnswerNotProvided)) return false;

      var question = questionData.get(criterion.questionId());

      if (!question.options().contains(answer))
         throw new InvalidAnswerException();
      return criterion.expectedAnswer().equals(answer);
   }

   public Question getQuestion(int criterionId) {
      var criterion = criterionData.get(criterionId);
      return questionData.get(criterion.questionId());
   }
}
