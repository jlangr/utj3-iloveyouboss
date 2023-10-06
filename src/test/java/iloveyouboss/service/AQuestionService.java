package iloveyouboss.service;

import iloveyouboss.data.QuestionData;
import iloveyouboss.domain.questions.YesNoQuestion;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class AQuestionService {
   @InjectMocks
   QuestionService questionService;

   @Mock
   QuestionData questionData;

   // START:timestamp
   @Test
   void attachesTimestampOnAdd() {
      questionService.addYesNoQuestion("got milk?");

      verify(questionData).add(new YesNoQuestion("got milk?", Instant.now()));
   }
   // END:timestamp
}
