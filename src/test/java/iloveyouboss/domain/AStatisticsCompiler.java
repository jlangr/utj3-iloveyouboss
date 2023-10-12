package iloveyouboss.domain;

import iloveyouboss.domain.questions.YesNoQuestion;
import iloveyouboss.service.CriterionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static iloveyouboss.domain.questions.YesNoQuestion.No;
import static iloveyouboss.domain.questions.YesNoQuestion.Yes;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class AStatisticsCompiler {
   @InjectMocks
   StatisticsCompiler compiler;
   @Mock
   CriterionService criterionService;

   Question tuitionQuestion = new YesNoQuestion(1, "Tuition reimbursement?");
   Question relocationQuestion = new YesNoQuestion(2, "Relocation package?");
   Criterion tuitionCriterion = new Criterion(tuitionQuestion.id(), Yes);
   Criterion relocationCriterion = new Criterion(relocationQuestion.id(), Yes);

   @Test
   void producesAnswerCountsByQuestionTextHistogram() {
      var answers = List.of(
         annotatedAnswer(tuitionCriterion, tuitionQuestion, Yes),
         annotatedAnswer(tuitionCriterion, tuitionQuestion, Yes),
         annotatedAnswer(tuitionCriterion, tuitionQuestion, Yes),
         annotatedAnswer(tuitionCriterion, tuitionQuestion, No),
         annotatedAnswer(relocationCriterion, relocationQuestion, Yes),
         annotatedAnswer(relocationCriterion, relocationQuestion, Yes));

      var statistics = compiler.answerCountsByQuestionText(answers);

      assertEquals(3, statistics.get(tuitionQuestion.text()).get(Yes));
      assertEquals(1, statistics.get(tuitionQuestion.text()).get(No));
      assertEquals(2, statistics.get(relocationQuestion.text()).get(Yes));
      assertEquals(0, statistics.get(relocationQuestion.text()).get(No));
   }

   AnnotatedAnswer annotatedAnswer(
      Criterion criterion, Question question, String answer) {
      return new AnnotatedAnswer(
         new Answer(criterion.id(), answer), question.text());
   }
}
