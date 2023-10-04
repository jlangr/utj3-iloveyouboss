package iloveyouboss.tools;

import iloveyouboss.data.AnswerData;
import iloveyouboss.data.CriterionData;
import iloveyouboss.data.YesNoQuestionData;
import iloveyouboss.domain.Answer;
import iloveyouboss.domain.Criterion;
import iloveyouboss.domain.questions.YesNoQuestion;
import iloveyouboss.service.AnswerService;
import iloveyouboss.service.CriterionService;
import iloveyouboss.service.StatisticsService;

public class RunStats {
   final static YesNoQuestionData questionData = new YesNoQuestionData();
   final static CriterionData criterionData = new CriterionData();
   final static AnswerData answerData = new AnswerData();

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

      var answerService = new AnswerService(
         new AnswerData(),
         new CriterionService(questionData, criterionData));

      System.out.println(new StatisticsService(answerService).answerHistogram());
   }
}
