package iloveyouboss.service;

import iloveyouboss.data.QuestionData;
import iloveyouboss.domain.Question;
import iloveyouboss.domain.questions.YesNoQuestion;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AQuestionService {
   // This cheap and slightly improper test double allows the
   // code to demonstrate a simple test without having to explain
   // the concept of test doubles yet
   class InMemoryQuestionData extends QuestionData {
      Map<Integer, Question> questions = new HashMap<>();

      @Override
      public int add(Question question) {
         questions.put(question.id(), question);
         return question.id();
      }

      @Override
      public Question get(int id) {
         return questions.get(id);
      }
   }
   QuestionService questionService = new QuestionService(new InMemoryQuestionData());

   // START:timestamp
   @Test
   void attachesTimestampOnAdd() {
      // START_HIGHLIGHT
      var now = Instant.now();
      questionService.setClock(Clock.fixed(now, ZoneOffset.UTC));
      // END_HIGHLIGHT
      var id = questionService.addYesNoQuestion("?");

      var result = questionService.getQuestion(id);

      // START_HIGHLIGHT
      assertEquals(new YesNoQuestion("?", now), result);
      // END_HIGHLIGHT
   }
   // END:timestamp
}
