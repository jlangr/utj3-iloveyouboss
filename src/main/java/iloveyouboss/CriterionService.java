package iloveyouboss;

import iloveyouboss.questions.QuestionData;
import iloveyouboss.questions.QuestionService;

import static iloveyouboss.questions.Question.AnswerNotProvided;

public class CriterionService {
   private QuestionData questionData;

   public CriterionService(QuestionData questionData) {
      this.questionData = questionData;
   }

   public boolean isMetBy(Criterion criterion, String answer) {
      if (answer.equals(AnswerNotProvided)) return false;

      var question = questionData.get(criterion.questionId());

      if (!question.options().contains(answer))
         throw new InvalidAnswerException();
      return criterion.expectedAnswer().equals(answer);
   }
}
