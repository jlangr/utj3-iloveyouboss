package iloveyouboss.stats;

import iloveyouboss.answers.Answer;
import iloveyouboss.Criterion;
import iloveyouboss.answers.AnnotatedAnswer;
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
   YesNoQuestion tuitionQuestion = new YesNoQuestion(1, "Tuition reimbursement?");
   YesNoQuestion relocationQuestion = new YesNoQuestion(2, "Relocation package?");

   @BeforeEach
   void createTuitionCriterion() {
      tuitionCriterion = new Criterion(tuitionQuestion.id(), Yes);
   }

   @BeforeEach
   void createRelocationCriterion() {
      relocationCriterion = new Criterion(relocationQuestion.id(), Yes);
   }

   @Test
   void createsHistogramByQuestion() {
      var answers = List.of(
         new AnnotatedAnswer(new Answer(tuitionCriterion, Yes), tuitionQuestion.text()),
         new AnnotatedAnswer(new Answer(tuitionCriterion, Yes), tuitionQuestion.text()),
         new AnnotatedAnswer(new Answer(tuitionCriterion, Yes), tuitionQuestion.text()),
         new AnnotatedAnswer(new Answer(tuitionCriterion, No), tuitionQuestion.text()),
         new AnnotatedAnswer(new Answer(relocationCriterion, Yes), relocationQuestion.text()),
         new AnnotatedAnswer(new Answer(relocationCriterion, Yes), relocationQuestion.text()));

      var statistics = compiler.answerCountsByQuestionText(answers);

      System.out.println(statistics);

      assertEquals(3, statistics.get(tuitionQuestion.text()).get(Yes));
      assertEquals(1, statistics.get(tuitionQuestion.text()).get(No));
      assertEquals(2, statistics.get(relocationQuestion.text()).get(Yes));
      assertEquals(0, statistics.get(relocationQuestion.text()).get(No));
   }
}
