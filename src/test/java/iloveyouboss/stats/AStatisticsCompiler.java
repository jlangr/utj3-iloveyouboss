/***
 * Excerpted from "Pragmatic Unit Testing in Java with JUnit",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material, 
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose. 
 * Visit http://www.pragmaticprogrammer.com/titles/utj2 for more book information.
***/
package iloveyouboss.stats;

import java.util.*;

import iloveyouboss.Answer;
import iloveyouboss.Criterion;
import iloveyouboss.questions.yesno.YesNoQuestion;
import static iloveyouboss.questions.yesno.YesNoQuestion.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AStatisticsCompiler {

   @Test
   public void createsHistogramByQuestion() {
      var compiler = new StatisticsCompiler();

      var criterion1 = new Criterion(new YesNoQuestion(1, "Tuition reimbursement"), Yes);
      var criterion2 = new Criterion(new YesNoQuestion(2, "Relocation package?"), Yes);

      var answers = new ArrayList<Answer>();
      answers.add(new Answer(criterion1, Yes));
      answers.add(new Answer(criterion1, Yes));
      answers.add(new Answer(criterion1, Yes));
      answers.add(new Answer(criterion1, No));
      answers.add(new Answer(criterion2, Yes));
      answers.add(new Answer(criterion2, Yes));

      var statistics = compiler.histogram(answers);

      System.out.println(statistics);
//      assertEquals(3, compiler.get("Tuition reimbursement?").get(Boolean.TRUE).get());
//      assertEquals(1, compiler.get("Tuition reimbursement?").get(Boolean.FALSE).get());
//      assertEquals(2, compiler.get("Relocation package?").get(Boolean.TRUE).get());
//      assertEquals(0, compiler.get("Relocation package?").get(Boolean.FALSE).get());
   }
}
