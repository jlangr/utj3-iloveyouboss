package iloveyouboss;

import iloveyouboss.criteria.Criteria;
import iloveyouboss.criteria.Criterion;
import iloveyouboss.criteria.CriterionData;
import iloveyouboss.criteria.CriterionService;
import iloveyouboss.questions.DuplicateQuestionException;
import iloveyouboss.questions.Question;
import iloveyouboss.questions.YesNoQuestionData;

import java.util.HashMap;
import java.util.Map;

import static iloveyouboss.questions.Question.AnswerNotProvided;

public class ProfileService {
   private Map<Integer, String> answers = new HashMap<>();
   private CriterionService criterionService;

   public ProfileService(YesNoQuestionData questionData, CriterionData criterionData) {
      criterionService = new CriterionService(questionData, criterionData);
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
