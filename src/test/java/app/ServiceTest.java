package app;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ServiceTest {
   @Mock
   YesNoQuestionData questionData;
   @Mock
   CriterionData criterionData;

   YesNoQuestion question = new YesNoQuestion(42, "When?");

   @InjectMocks
   Service service;

   // TODO the existence of the BeforeEach here generates failure
   @BeforeEach
   void createMockStuff() {
   }

   @Test
   void mockInjection() {
      when(questionData.get(42)).thenReturn(question);

      assertNotNull(service.getQuestion(42));
   }
}