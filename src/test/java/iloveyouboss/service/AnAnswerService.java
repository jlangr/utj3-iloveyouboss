package iloveyouboss.service;

import iloveyouboss.data.AnswerData;
import iloveyouboss.domain.Answer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class})
class AnAnswerService {
   @InjectMocks
   AnswerService answerService;
   @Mock
   AnswerData answerData;

   Answer answer1 = new Answer(1, "1");
   Answer answer2 = new Answer(2, "2");

   @Nested
   class RetrieveAll {
      List<Answer> answers = List.of(answer1, answer2);
      List<Answer> retrieved;

      @BeforeEach
      void retrieveAll() {
         when(answerData.getAll()).thenReturn(answers);

         retrieved = answerService.retrieveAll();
      }

      @Test
      void returnsListOfAnswersFromAnswerData() {
         assertEquals(answers, retrieved);
      }

      @Test
      void doesNotReturnSameInstance() {
         assertNotSame(answers, retrieved);
      }
   }
}