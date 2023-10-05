package iloveyouboss.service;

import iloveyouboss.data.CriterionData;
import iloveyouboss.data.QuestionData;
import iloveyouboss.domain.StatisticsCompiler;

import java.util.Map;

public class StatisticsService {
   final AnswerService answerService;
   final StatisticsCompiler statisticsCompiler =
      new StatisticsCompiler(
         new CriterionService(new QuestionData(), new CriterionData()));

   public StatisticsService(AnswerService answerService) {
      this.answerService = answerService;
   }

   // START_HIGHLIGHT
   public Map<String, Map<String, Integer>> answerHistogram() {
      var answers = answerService.retrieveAll();
      return statisticsCompiler.answerCountsByQuestionText(answers);
   }
   // END_HIGHLIGHT
}
