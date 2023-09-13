package iloveyouboss.stats;

import iloveyouboss.answers.AnnotatedAnswer;
import iloveyouboss.questions.QuestionService;

import java.util.*;

import static iloveyouboss.questions.yesno.YesNoQuestion.No;
import static iloveyouboss.questions.yesno.YesNoQuestion.Yes;
import static java.util.stream.Collectors.*;

public class StatisticsCompiler {
   private QuestionService questionService = new QuestionService();

   public Map<String, Map<String, Integer>> answerCountsByQuestionText(
         List<AnnotatedAnswer> answers) {
      return answers.stream().collect(
         toMap(this::questionText, this::histogramForAnswer, this::mergeHistograms));
   }

   private String questionText(AnnotatedAnswer answer) {
      // START_HIGHLIGHT
      return answer.questionText();
      // END_HIGHLIGHT
   }

   private Map<String, Integer> histogramForAnswer(AnnotatedAnswer answer) {
      var initialMap = new HashMap(Map.of(Yes, 0, No, 0));
      initialMap.put(answer.get().value(), 1);
      return initialMap;
   }

   private Map<String, Integer> mergeHistograms(
         Map<String, Integer> histogram, Map<String, Integer> histogram1) {
      var newHistogram = new HashMap<>(histogram);
      histogram1.forEach((k, v) ->
         newHistogram.merge(k, v, (count1, count2) -> count1 + count2));
      return newHistogram;
   }
}
