package iloveyouboss.answers;

import iloveyouboss.Criterion;
import iloveyouboss.questions.yesno.YesNoQuestion;

import java.util.List;

import static iloveyouboss.questions.yesno.YesNoQuestion.No;
import static iloveyouboss.questions.yesno.YesNoQuestion.Yes;

public class AnswersPersistence {
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
}
