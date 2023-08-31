/***
 * Excerpted from "Pragmatic Unit Testing in Java with JUnit",
 * published by The Pragmatic Bookshelf.
 * Copyrights apply to this code. It may not be used to create training material, 
 * courses, books, articles, and the like. Contact us if you are in doubt.
 * We make no guarantees that this code is fit for any purpose. 
 * Visit http://www.pragmaticprogrammer.com/titles/utj2 for more book information.
***/
package iloveyouboss.stats;

import iloveyouboss.Answer;
import iloveyouboss.questions.Question;
import iloveyouboss.questions.yesno.YesNoQuestion;

import java.util.*;
import java.util.concurrent.atomic.*;

public class StatisticsCompiler {
   static Question q1 = new YesNoQuestion(1, "Tuition reimbursement?");
   static Question q2 = new YesNoQuestion(2, "Relocation package?");

   class QuestionController {
      Question find(int id) {
         return id == 1 ? q1 : q2;
      }
   }

   private QuestionController controller = new QuestionController();

   public Map<String, Map<String, AtomicInteger>> answerTextToHistogram(List<Answer> answers) {
      var answerIdToHistogram = new HashMap<Integer, Map<String, AtomicInteger>>();
      answers.stream().forEach(answer -> update(answerIdToHistogram, answer));
      return convertHistogramIdsToText(answerIdToHistogram);
   }

   private void update(
         Map<Integer, Map<String, AtomicInteger>> stats,
         Answer answer) {
      histogramForAnswer(stats, answer)
         .get(answer.answer())
         .getAndIncrement();
   }

   private Map<String, AtomicInteger> histogramForAnswer(
         Map<Integer, Map<String, AtomicInteger>> stats, Answer answer) {
      var answerId = answer.criterion().question().id();
      if (stats.containsKey(answerId))
         return stats.get(answerId);

      var histogram = createNewHistogram();
      stats.put(answerId, histogram);
      return histogram;
   }

   private Map<String, AtomicInteger> createNewHistogram() {
      var histogram = new HashMap<String, AtomicInteger>();
      histogram.put(YesNoQuestion.Yes, new AtomicInteger(0));
      histogram.put(YesNoQuestion.No, new AtomicInteger(0));
      return histogram;
   }

   private Map<String, Map<String, AtomicInteger>> convertHistogramIdsToText(
      Map<Integer, Map<String, AtomicInteger>> answerIdToHistogram) {
      var textResponses = new HashMap<String, Map<String, AtomicInteger>>();
      answerIdToHistogram.keySet().stream().forEach(id ->
         textResponses.put(controller.find(id).text(), answerIdToHistogram.get(id)));
      return textResponses;
   }
}
