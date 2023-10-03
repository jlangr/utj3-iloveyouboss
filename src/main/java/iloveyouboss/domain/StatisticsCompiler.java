package iloveyouboss.domain;

import java.util.*;

import static iloveyouboss.domain.questions.YesNoQuestion.No;
import static iloveyouboss.domain.questions.YesNoQuestion.Yes;
import static java.util.stream.Collectors.*;

public class StatisticsCompiler {
   public Map<String, Map<String, Integer>> answerCountsByQuestionText(
      // START_HIGHLIGHT
         List<AnnotatedAnswer> answers) {
      // END_HIGHLIGHT
      return answers.stream().collect(
         // START_HIGHLIGHT
         toMap(AnnotatedAnswer::questionText,
            this::histogramForAnswer,
            this::mergeHistograms));
         // END_HIGHLIGHT
   }

   private Map<String, Integer> histogramForAnswer(AnnotatedAnswer answer) {
      var initialMap = new HashMap<>(Map.of(Yes, 0, No, 0));
      initialMap.put(answer.get().text(), 1);
      return initialMap;
   }

   private Map<String, Integer> mergeHistograms(
         Map<String, Integer> histogram, Map<String, Integer> histogram1) {
      var newHistogram = new HashMap<>(histogram);
      histogram1.forEach((k, v) ->
         newHistogram.merge(k, v, Integer::sum));
      return newHistogram;
   }
}
