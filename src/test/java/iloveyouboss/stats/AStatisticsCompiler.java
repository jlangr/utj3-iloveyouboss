/***
 * Excerpted from "Pragmatic Unit Testing in Java with JUnit",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material, 
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose. 
 * Visit http://www.pragmaticprogrammer.com/titles/utj2 for more book information.
***/
package iloveyouboss.stats;

import java.io.Serializable;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import iloveyouboss.Answer;
import iloveyouboss.Criterion;
import iloveyouboss.questions.yesno.YesNoQuestion;
import static iloveyouboss.questions.yesno.YesNoQuestion.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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

      var statistics = compiler.answerTextToHistogram(answers);

      assertEquals(3, statistics.get(tuitionCriterion.questionText()).get(Yes).get());
      assertEquals(1, statistics.get(tuitionCriterion.questionText()).get(No).get());
      assertEquals(2, statistics.get(relocationCriterion.questionText()).get(Yes).get());
      assertEquals(0, statistics.get(relocationCriterion.questionText()).get(No).get());
   }
}
