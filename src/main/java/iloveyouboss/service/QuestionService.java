package iloveyouboss.service;

import iloveyouboss.data.QuestionData;
import iloveyouboss.domain.Question;
import iloveyouboss.domain.questions.YesNoQuestion;

import java.time.Clock;

public class QuestionService {
   // START_HIGHLIGHT
   private Clock clock = Clock.systemUTC();
   // END_HIGHLIGHT
   private final QuestionData questionData;

   public QuestionService(QuestionData questionData) {
      this.questionData = questionData;
   }

   public int addYesNoQuestion(String text) {
      // START_HIGHLIGHT
      return questionData.add(new YesNoQuestion(text, clock.instant()));
      // END_HIGHLIGHT
   }

   public Question getQuestion(int id) {
      return questionData.get(id);
   }
}
