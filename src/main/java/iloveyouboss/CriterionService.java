package iloveyouboss;

import iloveyouboss.questions.QuestionService;

import static iloveyouboss.questions.Question.AnswerNotProvided;

public class CriterionService {
   private QuestionService questionService;

   public CriterionService(QuestionService questionService) {
      this.questionService = questionService;
   }

   public boolean isMetBy(Criterion criterion, String answer) {
      if (answer.equals(AnswerNotProvided)) return false;

      var question = questionService.get(criterion.questionId());

      if (!question.options().contains(answer))
         throw new InvalidAnswerException();
      return criterion.expectedAnswer().equals(answer);
   }
}
