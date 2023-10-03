package iloveyouboss.service;

import iloveyouboss.data.CriterionData;
import iloveyouboss.data.Data;
import iloveyouboss.domain.Criterion;
import iloveyouboss.domain.InvalidAnswerException;
import iloveyouboss.domain.Question;

import static iloveyouboss.domain.Question.AnswerNotProvided;

public class CriterionService {
   private final Data<?> questionData;
   private final CriterionData criterionData;

   public CriterionService(Data<?> questionData, CriterionData criterionData) {
      this.questionData = questionData;
      this.criterionData = criterionData;
   }

   public boolean isMetBy(Criterion criterion, String answer) {
      if (answer.equals(AnswerNotProvided)) return false;

      var question = (Question)questionData.get(criterion.questionId());

      if (!question.answerOptions().contains(answer))
         throw new InvalidAnswerException();
      return criterion.expectedAnswer().equals(answer);
   }

   public Question getQuestion(int criterionId) {
      var criterion = criterionData.get(criterionId);
      return (Question)questionData.get(criterion.questionId());
   }
}
