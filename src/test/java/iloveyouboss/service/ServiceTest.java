package iloveyouboss.service;

import iloveyouboss.Service;
import iloveyouboss.data.CriterionData;
import iloveyouboss.data.YesNoQuestionData;
import iloveyouboss.domain.questions.YesNoQuestion;
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

   @BeforeEach
   void createMockStuff() {
//      MockitoAnnotations.initMocks(this);
//      criterionService = new CriterionService(questionData, criterionData);
   }

   @Test
   void mockInjection() {
      when(questionData.get(42)).thenReturn(question);

      assertNotNull(service.getQuestion(42));
   }
}