package iloveyouboss.service;

import iloveyouboss.domain.InvalidAnswerException;
import iloveyouboss.data.CriterionData;
import iloveyouboss.domain.Criterion;
import iloveyouboss.domain.Question;
import iloveyouboss.data.YesNoQuestionData;

import static iloveyouboss.domain.Question.AnswerNotProvided;

public class CriterionService {
   private final YesNoQuestionData questionData;
   private final CriterionData criterionData;

   public CriterionService(YesNoQuestionData questionData, CriterionData criterionData) {
      this.questionData = questionData;
      this.criterionData = criterionData;
   }

   public boolean isMetBy(Criterion criterion, String answer) {
      if (answer.equals(AnswerNotProvided)) return false;

      var question = questionData.get(criterion.questionId());

      if (!question.answerOptions().contains(answer))
         throw new InvalidAnswerException();
      return criterion.expectedAnswer().equals(answer);
   }

   public Question getQuestion(int criterionId) {
      var criterion = criterionData.get(criterionId);
      return questionData.get(criterion.questionId());
   }
}
