package iloveyouboss.service;

import iloveyouboss.data.AnswerData;
import iloveyouboss.data.CriterionData;
import iloveyouboss.data.YesNoQuestionData;
import iloveyouboss.domain.StatisticsCompiler;

import java.util.Map;

// TODO test?
public class StatisticsService {
   AnswerService answerService = new AnswerService(
      new AnswerData(),
      new CriterionService(new YesNoQuestionData(), new CriterionData()));
   StatisticsCompiler statisticsCompiler = new StatisticsCompiler();

   public Map<String, Map<String, Integer>> answerHistogram() {
      var answers = answerService.retrieveAllAnnotated();
      return statisticsCompiler.answerCountsByQuestionText(answers);
   }

   public static void main(String[] args) {
      System.out.println(new StatisticsService().answerHistogram());
   }
}
