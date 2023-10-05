package iloveyouboss.service;

import iloveyouboss.data.QuestionData;
import iloveyouboss.domain.questions.YesNoQuestion;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class AQuestionService {
   @InjectMocks
   QuestionService service;

   @Mock
   QuestionData questionData;

   @Test
   void attachesCreationTimestampToAddedQuestions() {
      // START_HIGHLIGHT
      var now = Instant.now();
      service.setClock(Clock.fixed(now, ZoneId.of("America/Denver")));
      // END_HIGHLIGHT

      service.addYesNoQuestion("got milk?");

      // START_HIGHLIGHT
      verify(questionData).add(new YesNoQuestion("got milk?", now));
      // END_HIGHLIGHT
   }
}
