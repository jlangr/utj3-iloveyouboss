package iloveyouboss.service;

import iloveyouboss.data.AnswerData;
import iloveyouboss.domain.AnnotatedAnswer;
import iloveyouboss.domain.Answer;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class AnswerService {
   private final AnswerData answerData;
   private final CriterionService criterionService;

   public AnswerService(AnswerData answerData, CriterionService criterionService) {
      this.answerData = answerData;
      this.criterionService = criterionService;
   }

   // TODO test
   // START_HIGHLIGHT
   public List<AnnotatedAnswer> retrieveAllAnnotated() {
      return answerData.getAll().stream()
         .map(this::annotate)
         .collect(toList());
   }

   private AnnotatedAnswer annotate(Answer answer) {
      var question = criterionService.getQuestion(answer.criterionId());
      return new AnnotatedAnswer(answer, question.text());
   }
   // END_HIGHLIGHT

   // TODO no longer used?
   public List<Answer> retrieveAll() {
      return answerData.getAll().stream().collect(toList());
   }
}
