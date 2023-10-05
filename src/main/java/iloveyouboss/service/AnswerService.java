package iloveyouboss.service;

import iloveyouboss.data.AnswerData;
import iloveyouboss.domain.AnnotatedAnswer;
import iloveyouboss.domain.Answer;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class AnswerService {
   private final AnswerData answerData;
   private final CriterionService criterionService;

   public AnswerService(AnswerData answerData, CriterionService criterionService) {
      this.answerData = answerData;
      this.criterionService = criterionService;
   }

   // START:retrieveAllAnnotated
   public List<AnnotatedAnswer> retrieveAllAnnotated() {
      return answerData.getAll().stream()
         .map(this::annotate)
         .collect(toList());
   }

   private AnnotatedAnswer annotate(Answer answer) {
      var question = criterionService.getQuestion(answer.criterionId());
      return new AnnotatedAnswer(answer, question.text());
   }
   // END:retrieveAllAnnotated

   public List<Answer> retrieveAll() {
      return new ArrayList<>(answerData.getAll());
   }
}
