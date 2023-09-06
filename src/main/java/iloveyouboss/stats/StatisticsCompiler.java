package iloveyouboss.stats;

import iloveyouboss.Answer;

import java.util.*;
import java.util.concurrent.atomic.*;

import static iloveyouboss.questions.yesno.YesNoQuestion.No;
import static iloveyouboss.questions.yesno.YesNoQuestion.Yes;

public class StatisticsCompiler {
   private QuestionController controller = new QuestionController();

   public Map<String, Map<String, AtomicInteger>> answerTextToHistogram(
         List<Answer> answers) {
      var answerIdToHistogram = new HashMap<Integer, Map<String, AtomicInteger>>();
      answers.stream().forEach(answer -> update(answer, answerIdToHistogram));
      return convertHistogramIdsToText(answerIdToHistogram);
   }

   private void update(
         Answer answer, Map<Integer, Map<String, AtomicInteger>> stats) {
      histogramForAnswer(stats, answer)
         .get(answer.answer())
         .getAndIncrement();
   }

   private Map<String, AtomicInteger> histogramForAnswer(
         Map<Integer, Map<String, AtomicInteger>> stats, Answer answer) {
      return stats.computeIfAbsent(
         answer.criterion().question().id(),
         _id -> createNewHistogram());
   }

   private Map<String, AtomicInteger> createNewHistogram() {
      return Map.of(
         Yes, new AtomicInteger(0),
         No, new AtomicInteger(0));
   }

   private Map<String, Map<String, AtomicInteger>> convertHistogramIdsToText(
      Map<Integer, Map<String, AtomicInteger>> answerIdToHistogram) {
      var textResponses = new HashMap<String, Map<String, AtomicInteger>>();
      answerIdToHistogram.keySet().stream().forEach(id ->
         textResponses.put(controller.find(id).text(), answerIdToHistogram.get(id)));
      return textResponses;
   }
}
