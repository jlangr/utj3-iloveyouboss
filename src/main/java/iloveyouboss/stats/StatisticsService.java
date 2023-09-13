package iloveyouboss.stats;

import iloveyouboss.answers.AnswerService;

import java.util.Map;

public class StatisticsService {
   AnswerService answerService = new AnswerService();
   StatisticsCompiler statisticsCompiler = new StatisticsCompiler();

   public Map<String, Map<String, Integer>> answerHistogram() {
      var answers = answerService.retrieveAllAnnotated();
      return statisticsCompiler.answerCountsByQuestionText(answers);
   }

   public static void main(String[] args) {
      System.out.println(new StatisticsService().answerHistogram());
   }
}
