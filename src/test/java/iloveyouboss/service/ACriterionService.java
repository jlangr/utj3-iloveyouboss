package iloveyouboss.service;

import iloveyouboss.data.CriterionData;
import iloveyouboss.data.YesNoQuestionData;
import iloveyouboss.domain.Criterion;
import iloveyouboss.domain.questions.YesNoQuestion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static iloveyouboss.domain.questions.YesNoQuestion.Yes;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ACriterionService {
   @Mock
   YesNoQuestionData questionData;
   @Mock
   CriterionData criterionData;

   YesNoQuestion question = new YesNoQuestion(42, "When?");
   Criterion criterion = new Criterion(1, 42, Yes);
   @InjectMocks
   CriterionService criterionService;

   @BeforeEach
   void createMockStuff() {
//      MockitoAnnotations.initMocks(this);
//      criterionService = new CriterionService(questionData, criterionData);
   }

   @Test
   void test3() {
      System.out.println("3");
//      when(criterionData.get(criterion.id())).thenReturn(criterion);
      when(questionData.get(42)).thenReturn(question);

      var retrieved = criterionService.getQuestion(criterion.id());
      assertNotNull(retrieved);
   }
}