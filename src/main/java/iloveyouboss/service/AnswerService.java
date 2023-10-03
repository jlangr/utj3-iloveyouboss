package iloveyouboss.service;

import iloveyouboss.domain.AnnotatedAnswer;
import iloveyouboss.domain.Answer;
import iloveyouboss.data.AnswerData;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class AnswerService {
   private final AnswerData answerData;
   private final CriterionService criterionService;

   public AnswerService(AnswerData answerData, CriterionService criterionService) {
      this.answerData = answerData;
      this.criterionService = criterionService;
   }

   public List<AnnotatedAnswer> retrieveAllAnnotated() {
      System.out.println("ANSWER DATA: " + answerData.getAll());
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
