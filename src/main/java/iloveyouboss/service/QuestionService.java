package iloveyouboss.service;

import iloveyouboss.data.QuestionData;
import iloveyouboss.domain.questions.YesNoQuestion;

// START:timestamp
// ...
import java.time.Clock;

public class QuestionService {
   private Clock clock = Clock.systemUTC();
   // ...
// END:timestamp
   private final QuestionData questionData;

   public QuestionService(QuestionData questionData) {
      this.questionData = questionData;
   }
   //START:timestamp

   public void addYesNoQuestion(String text) {
      questionData.add(new YesNoQuestion(text, clock.instant()));
   }
}
// END:timestamp