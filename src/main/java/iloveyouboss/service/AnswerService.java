package iloveyouboss.service;

import iloveyouboss.data.AnswerData;
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

   public List<Answer> retrieveAll() {
      return answerData.getAll()
         .stream()
         .collect(toList());
   }
}
