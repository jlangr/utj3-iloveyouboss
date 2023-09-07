package iloveyouboss.stats;

import iloveyouboss.Answer;
import java.util.*;
import java.util.concurrent.atomic.*;

import static iloveyouboss.questions.yesno.YesNoQuestion.No;
import static iloveyouboss.questions.yesno.YesNoQuestion.Yes;

public class StatisticsCompiler {
   private QuestionController controller = new QuestionController();

   public Map<String, Map<String, AtomicInteger>> answerCountsByQuestionText(
         List<Answer> answers) {
      var collectingHistogram = new HashMap<String, Map<String, AtomicInteger>>();
      answers.stream().forEach(answer -> tally(answer, collectingHistogram));
      return collectingHistogram;
   }

   private void tally(
         Answer answer, Map<String, Map<String, AtomicInteger>> stats) {
      histogramForAnswer(stats, answer)
         .get(answer.value())
         .getAndIncrement();
   }

   private Map<String, AtomicInteger> histogramForAnswer(
         Map<String, Map<String, AtomicInteger>> stats, Answer answer) {
      return stats.computeIfAbsent(
         // START_HIGHLIGHT
         controller.find(answer.question().id()).text(),
         // END_HIGHLIGHT
         _id -> Map.of(
            Yes, new AtomicInteger(0),
            No, new AtomicInteger(0)));
   }
}
