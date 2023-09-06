package iloveyouboss.stats;

import iloveyouboss.Answer;
import iloveyouboss.Criterion;
import iloveyouboss.questions.yesno.YesNoQuestion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static iloveyouboss.questions.yesno.YesNoQuestion.No;
import static iloveyouboss.questions.yesno.YesNoQuestion.Yes;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AStatisticsCompiler {
   StatisticsCompiler compiler = new StatisticsCompiler();
   Criterion tuitionCriterion;
   Criterion relocationCriterion;

   @BeforeEach
   void createTuitionCriterion() {
      tuitionCriterion = new Criterion(new YesNoQuestion(1, "Tuition reimbursement?"), Yes);
   }

   @BeforeEach
   void createRelocationCriterion() {
      relocationCriterion = new Criterion(new YesNoQuestion(2, "Relocation package?"), Yes);
   }

   @Test
   void createsHistogramByQuestion() {
      var answers = List.of(
         new Answer(tuitionCriterion, Yes),
         new Answer(tuitionCriterion, Yes),
         new Answer(tuitionCriterion, Yes),
         new Answer(tuitionCriterion, No),
         new Answer(relocationCriterion, Yes),
         new Answer(relocationCriterion, Yes));

      var statistics = compiler.questionTextToHistogram(answers);

      assertEquals(3, statistics.get(tuitionCriterion.questionText()).get(Yes).get());
      assertEquals(1, statistics.get(tuitionCriterion.questionText()).get(No).get());
      assertEquals(2, statistics.get(relocationCriterion.questionText()).get(Yes).get());
      assertEquals(0, statistics.get(relocationCriterion.questionText()).get(No).get());
   }
}
