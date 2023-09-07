package iloveyouboss.stats;

import iloveyouboss.Answer;

import java.util.*;
import java.util.concurrent.atomic.*;

import static iloveyouboss.questions.yesno.YesNoQuestion.No;
import static iloveyouboss.questions.yesno.YesNoQuestion.Yes;

public class StatisticsCompiler {
   private QuestionController controller = new QuestionController();

   public Map<String, Map<String, AtomicInteger>> questionTextToHistogram(
         List<Answer> answers) {
      var collectingHistogram = new HashMap<Integer, Map<String, AtomicInteger>>();
      answers.stream().forEach(answer -> tally(answer, collectingHistogram));
      return convertQuestionIdsToQuestionText(collectingHistogram);
   }

   private void tally(
         Answer answer, Map<Integer, Map<String, AtomicInteger>> stats) {
      histogramForAnswer(stats, answer)
         .get(answer.value())
         .getAndIncrement();
   }

   private Map<String, AtomicInteger> histogramForAnswer(
         Map<Integer, Map<String, AtomicInteger>> stats, Answer answer) {
      return stats.computeIfAbsent(
         answer.questionId(),
         _id -> createNewHistogram());
   }

   private Map<String, AtomicInteger> createNewHistogram() {
      return Map.of(
         Yes, new AtomicInteger(0),
         No, new AtomicInteger(0));
   }

   private Map<String, Map<String, AtomicInteger>> convertQuestionIdsToQuestionText(
      Map<Integer, Map<String, AtomicInteger>> questionIdToHistogram) {
      var textResponses = new HashMap<String, Map<String, AtomicInteger>>();
      questionIdToHistogram.keySet().stream().forEach(id ->
         textResponses.put(controller.find(id).text(), questionIdToHistogram.get(id)));
      return textResponses;
   }
}
