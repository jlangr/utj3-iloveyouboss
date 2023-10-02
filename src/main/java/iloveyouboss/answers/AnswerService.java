package iloveyouboss.answers;

import iloveyouboss.CriterionService;
import iloveyouboss.questions.QuestionData;

import java.util.List;

import static java.util.stream.Collectors.toList;

/*
   YesNoQuestion tuitionQuestion = new YesNoQuestion(1, "Tuition reimbursement?");
   YesNoQuestion relocationQuestion = new YesNoQuestion(2, "Relocation package?");
   Criterion tuitionCriterion = new Criterion(relocationQuestion.id(), Yes);
   Criterion relocationCriterion = new Criterion(tuitionQuestion.id(), Yes);

   public List<Answer> retrieveAll() {
      return List.of(
         new Answer(tuitionCriterion, Yes),
         new Answer(tuitionCriterion, Yes),
         new Answer(tuitionCriterion, Yes),
         new Answer(tuitionCriterion, No),
         new Answer(relocationCriterion, Yes),
         new Answer(relocationCriterion, Yes));
   }
 */

// TODO test
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
