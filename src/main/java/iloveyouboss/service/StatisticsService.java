package iloveyouboss.service;

import iloveyouboss.data.AnswerData;
import iloveyouboss.data.CriterionData;
import iloveyouboss.data.YesNoQuestionData;
import iloveyouboss.domain.Answer;
import iloveyouboss.domain.Criterion;
import iloveyouboss.domain.StatisticsCompiler;
import iloveyouboss.domain.questions.YesNoQuestion;

import java.util.Map;

public class StatisticsService {
   final AnswerService answerService = new AnswerService(
      new AnswerData(),
      new CriterionService(new YesNoQuestionData(), new CriterionData()));

   final static YesNoQuestionData questionData = new YesNoQuestionData();
   final static CriterionData criterionData = new CriterionData();
   final static AnswerData answerData = new AnswerData();

   final StatisticsCompiler statisticsCompiler = new StatisticsCompiler(questionData, criterionData);

   public Map<String, Map<String, Integer>> answerHistogram() {
      var answers = answerService.retrieveAll();
      return statisticsCompiler.answerCountsByQuestionText(answers);
   }

   public static void main(String[] args) {
      var whoId = questionData.add(new YesNoQuestion("Who?"));
      var whenId = questionData.add(new YesNoQuestion("When?"));
      var whoCriterionId = criterionData.add(new Criterion(whoId, "everyone"));
      var whenCriterionId = criterionData.add(new Criterion(whenId, "now"));
      answerData.add(new Answer(whoCriterionId, "everyone"));
      answerData.add(new Answer(whoCriterionId, "everyone"));
      answerData.add(new Answer(whoCriterionId, "no one"));
      answerData.add(new Answer(whenCriterionId, "now"));
      answerData.add(new Answer(whenCriterionId, "later"));

      System.out.println(new StatisticsService().answerHistogram());
   }
}
