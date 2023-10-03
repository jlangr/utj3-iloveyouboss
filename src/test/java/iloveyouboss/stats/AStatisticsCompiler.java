package iloveyouboss.stats;

import iloveyouboss.answers.AnnotatedAnswer;
import iloveyouboss.answers.Answer;
import iloveyouboss.questions.yesno.YesNoQuestion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static iloveyouboss.questions.yesno.YesNoQuestion.No;
import static iloveyouboss.questions.yesno.YesNoQuestion.Yes;
import static org.junit.jupiter.api.Assertions.assertEquals;

// TODO backport in earlier versions
public class AStatisticsCompiler {
   StatisticsCompiler compiler;
   iloveyouboss.criteria.Criterion tuitionCriterion;
   iloveyouboss.criteria.Criterion relocationCriterion;
   YesNoQuestion tuitionQuestion = new YesNoQuestion(1, "Tuition reimbursement?");
   YesNoQuestion relocationQuestion = new YesNoQuestion(2, "Relocation package?");

   @BeforeEach
   void createStatisticsCompiler() {
      compiler = new StatisticsCompiler();
   }

   @BeforeEach
   void createTuitionCriterion() {
      tuitionCriterion = new iloveyouboss.criteria.Criterion(1, tuitionQuestion.id(), Yes);
   }

   @BeforeEach
   void createRelocationCriterion() {
      relocationCriterion = new iloveyouboss.criteria.Criterion(2, relocationQuestion.id(), Yes);
   }

   // START:test
   @Test
   void createsHistogramByQuestion() {
      var answers = List.of(
         new AnnotatedAnswer(new Answer(1, tuitionCriterion.id(), Yes), tuitionQuestion.text()),
         new AnnotatedAnswer(new Answer(2, tuitionCriterion.id(), Yes), tuitionQuestion.text()),
         new AnnotatedAnswer(new Answer(3, tuitionCriterion.id(), Yes), tuitionQuestion.text()),
         new AnnotatedAnswer(new Answer(4, tuitionCriterion.id(), No), tuitionQuestion.text()),
         new AnnotatedAnswer(new Answer(5, relocationCriterion.id(), Yes), relocationQuestion.text()),
         new AnnotatedAnswer(new Answer(6, relocationCriterion.id(), Yes), relocationQuestion.text()));

      var statistics = compiler.answerCountsByQuestionText(answers);

      System.out.println(statistics);

      assertEquals(3, statistics.get(tuitionQuestion.text()).get(Yes));
      assertEquals(1, statistics.get(tuitionQuestion.text()).get(No));
      assertEquals(2, statistics.get(relocationQuestion.text()).get(Yes));
      assertEquals(0, statistics.get(relocationQuestion.text()).get(No));
   }
   // END:test
}
