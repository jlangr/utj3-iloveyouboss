package iloveyouboss.service;

import iloveyouboss.data.QuestionData;
import iloveyouboss.domain.questions.YesNoQuestion;

import java.time.Clock;

public class QuestionService {
   private final QuestionData questionData;
   private Clock clock = Clock.systemUTC();

   public QuestionService(QuestionData questionData) {
      this.questionData = questionData;
   }

   public void addYesNoQuestion(String text) {
      var question = new YesNoQuestion(text, clock.instant());

      questionData.add(question);

   }
}
