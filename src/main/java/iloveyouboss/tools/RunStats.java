package iloveyouboss.tools;

import iloveyouboss.data.AnswerData;
import iloveyouboss.data.QuestionData;
import iloveyouboss.domain.Answer;
import iloveyouboss.domain.questions.YesNoQuestion;
import iloveyouboss.service.AnswerService;
import iloveyouboss.service.StatisticsService;

public class RunStats {
   final static QuestionData questionData = new QuestionData();
   final static AnswerData answerData = new AnswerData();

   public static void main(String[] args) {
      questionData.add(new YesNoQuestion("Who?"));
      questionData.add(new YesNoQuestion("When?"));
      answerData.add(new Answer(100, "everyone"));
      answerData.add(new Answer(100, "everyone"));
      answerData.add(new Answer(100, "no one"));
      answerData.add(new Answer(101, "now"));
      answerData.add(new Answer(101, "later"));

      var answerService = new AnswerService(new AnswerData());

      System.out.println(new StatisticsService(answerService).answerHistogram());
   }
}
