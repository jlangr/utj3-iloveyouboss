package iloveyouboss.domain;

import iloveyouboss.data.CriterionData;
import iloveyouboss.data.Data;
import iloveyouboss.service.CriterionService;

import java.util.*;

import static iloveyouboss.domain.questions.YesNoQuestion.No;
import static iloveyouboss.domain.questions.YesNoQuestion.Yes;
import static java.util.stream.Collectors.*;

public class StatisticsCompiler {
   // START_HIGHLIGHT
   final CriterionService criterionService;
   // END_HIGHLIGHT

   // START_HIGHLIGHT
   public StatisticsCompiler(
         Data<? extends Question> questionData, CriterionData criterionData) {
      criterionService = new CriterionService(questionData, criterionData);
   }
   // END_HIGHLIGHT

   public Map<String, Map<String, Integer>> answerCountsByQuestionText(
         List<Answer> answers) {
      return answers.stream().collect(
         toMap(this::questionText,
            this::histogramForAnswer,
            this::mergeHistograms));
   }

   private String questionText(Answer answer) {
      // START_HIGHLIGHT
      return criterionService.getQuestion(answer.criterionId()).text();
      // END_HIGHLIGHT
   }

   private Map<String, Integer> histogramForAnswer(Answer answer) {
      var initialMap = new HashMap<>(Map.of(Yes, 0, No, 0));
      initialMap.put(answer.text(), 1);
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
