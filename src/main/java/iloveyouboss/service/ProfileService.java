package iloveyouboss.service;

import iloveyouboss.domain.Criteria;
import iloveyouboss.domain.Criterion;
import iloveyouboss.domain.DuplicateQuestionException;
import iloveyouboss.domain.Question;

import java.util.HashMap;
import java.util.Map;

import static iloveyouboss.domain.Question.AnswerNotProvided;

public class ProfileService {
   private final Map<Integer, String> answers = new HashMap<>();
   private final CriterionService criterionService;

   public ProfileService(CriterionService criterionService) {
      this.criterionService = criterionService;
   }

   public boolean matches(Criteria criteria) {
      return criteria.stream()
         .filter(criterion -> !criterion.isOptional())
         .allMatch(criterion -> criterionService.isMetBy(criterion, answerFor(criterion)));
   }

   public void answer(Question question, String answer) {
      if (answers.containsKey(question.id()))
         throw new DuplicateQuestionException();
      answers.put(question.id(), answer);
   }

   public String answerFor(Criterion criterion) {
      return answers.getOrDefault(criterion.questionId(), AnswerNotProvided);
   }
}
