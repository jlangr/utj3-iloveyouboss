package iloveyouboss.answers;

import iloveyouboss.criteria.CriterionService;
import iloveyouboss.questions.YesNoQuestionData;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class AnswerService {
   private AnswerData answerData = new AnswerData();
   private YesNoQuestionData questionData = new YesNoQuestionData();
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
