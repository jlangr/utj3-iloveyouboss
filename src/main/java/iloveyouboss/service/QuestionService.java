package iloveyouboss.service;

import iloveyouboss.data.QuestionData;
import iloveyouboss.domain.questions.YesNoQuestion;

// START:QuestionService
// ...
import java.time.Clock;

public class QuestionService {
   private Clock clock = Clock.systemUTC();
   // ...
// END:QuestionService
   private final QuestionData questionData;

   public QuestionService(QuestionData questionData) {
      this.questionData = questionData;
   }
// START:QuestionService

   public void addYesNoQuestion(String text) {
      questionData.add(new YesNoQuestion(text, clock.instant()));
   }

   // START_HIGHLIGHT
   public void setClock(Clock clock) {
      this.clock = clock;
   }
   // END_HIGHLIGHT
}
// END:QuestionService
