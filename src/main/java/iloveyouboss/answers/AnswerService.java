package iloveyouboss.answers;

import iloveyouboss.CriterionService;
import iloveyouboss.questions.QuestionData;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class AnswerService {
   private AnswerData answerData = new AnswerData();
   private QuestionData questionData = new QuestionData();
   private CriterionService criterionService = new CriterionService(questionData);

   public List<AnnotatedAnswer> retrieveAllAnnotated() {
      return answerData.getAll()
         .stream()
         .map(this::createAnnotatedAnswer)
         .collect(toList());
   }

   private AnnotatedAnswer createAnnotatedAnswer(Answer answer) {
      var question = criterionService.getQuestion(answer.criterionId());
      return new AnnotatedAnswer(answer, question.text());
   }
}
