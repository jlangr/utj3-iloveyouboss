package iloveyouboss.service;

import iloveyouboss.domain.StatisticsCompiler;
import java.util.Map;

public class StatisticsService {
   final AnswerService answerService;
   final StatisticsCompiler statisticsCompiler = new StatisticsCompiler();

   public StatisticsService(AnswerService answerService) {
      this.answerService = answerService;
   }

   // START_HIGHLIGHT
   public Map<String, Map<String, Integer>> answerHistogram() {
      var answers = answerService.retrieveAllAnnotated();
      return statisticsCompiler.answerCountsByQuestionText(answers);
   }
   // END_HIGHLIGHT
}
