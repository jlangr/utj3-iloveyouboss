package iloveyouboss.domain;

import iloveyouboss.domain.questions.YesNoQuestion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static iloveyouboss.domain.questions.YesNoQuestion.No;
import static iloveyouboss.domain.questions.YesNoQuestion.Yes;
import static org.junit.jupiter.api.Assertions.assertEquals;

// START_HIGHLIGHT
public class AStatisticsCompiler {
   // END_HIGHLIGHT
   StatisticsCompiler compiler = new StatisticsCompiler();

   Criterion tuitionCriterion;
   Criterion relocationCriterion;
   YesNoQuestion tuitionQuestion = new YesNoQuestion(1, "Tuition reimbursement?");
   YesNoQuestion relocationQuestion = new YesNoQuestion(2, "Relocation package?");

   @BeforeEach
   void createTuitionCriterion() {
      tuitionCriterion = new Criterion(1, tuitionQuestion.id(), Yes);
   }

   @BeforeEach
   void createRelocationCriterion() {
      relocationCriterion = new Criterion(2, relocationQuestion.id(), Yes);
   }

   @Test
   void createsHistogramByQuestion() {
      var answers = List.of(
         // START_HIGHLIGHT
         new AnnotatedAnswer(new Answer(1, tuitionCriterion.id(), Yes), tuitionQuestion.text()),
         new AnnotatedAnswer(new Answer(2, tuitionCriterion.id(), Yes), tuitionQuestion.text()),
         new AnnotatedAnswer(new Answer(3, tuitionCriterion.id(), Yes), tuitionQuestion.text()),
         new AnnotatedAnswer(new Answer(4, tuitionCriterion.id(), No), tuitionQuestion.text()),
         new AnnotatedAnswer(new Answer(5, relocationCriterion.id(), Yes), relocationQuestion.text()),
         new AnnotatedAnswer(new Answer(6, relocationCriterion.id(), Yes), relocationQuestion.text()));
         // END_HIGHLIGHT

      var statistics = compiler.answerCountsByQuestionText(answers);

      assertEquals(3, statistics.get(tuitionQuestion.text()).get(Yes));
      assertEquals(1, statistics.get(tuitionQuestion.text()).get(No));
      assertEquals(2, statistics.get(relocationQuestion.text()).get(Yes));
      assertEquals(0, statistics.get(relocationQuestion.text()).get(No));
   }
}
