package iloveyouboss.stats;

import iloveyouboss.answers.AnswerData;
import iloveyouboss.answers.AnswerService;
import iloveyouboss.criteria.CriterionData;
import iloveyouboss.criteria.CriterionService;
import iloveyouboss.questions.YesNoQuestionData;

import java.util.Map;

public class StatisticsService {
   // TODO live stuff
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
