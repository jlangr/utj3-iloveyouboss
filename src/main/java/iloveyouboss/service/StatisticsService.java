package iloveyouboss.service;

import iloveyouboss.data.AnswerData;
import iloveyouboss.data.CriterionData;
import iloveyouboss.data.QuestionData;
import iloveyouboss.domain.StatisticsCompiler;

import java.util.Map;

public class StatisticsService {
   final AnswerService answerService = new AnswerService(
      new AnswerData(),
      new CriterionService(new QuestionData(), new CriterionData()));

   final static QuestionData questionData = new QuestionData();
   final static CriterionData criterionData = new CriterionData();
   final static AnswerData answerData = new AnswerData();
   final static CriterionService criterionService = new CriterionService(questionData, criterionData);

   final StatisticsCompiler statisticsCompiler = new StatisticsCompiler(criterionService);

   public Map<String, Map<String, Integer>> answerHistogram() {
      var answers = answerService.retrieveAll();
      return statisticsCompiler.answerCountsByQuestionText(answers);
   }
}
