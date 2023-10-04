package iloveyouboss.service;

import iloveyouboss.data.QuestionData;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class AQuestionService {
   @InjectMocks
   QuestionService service;

   @Mock
   QuestionData questionData;

   @Test
   void attachesCreationTimestampToAddedQuestions() {
      service.addYesNoQuestion("got milk?");

   }
}
