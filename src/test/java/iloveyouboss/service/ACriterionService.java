package iloveyouboss.service;

import iloveyouboss.data.CriterionData;
import iloveyouboss.data.YesNoQuestionData;
import iloveyouboss.domain.Criterion;
import iloveyouboss.domain.questions.YesNoQuestion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static iloveyouboss.domain.questions.YesNoQuestion.No;
import static iloveyouboss.domain.questions.YesNoQuestion.Yes;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ACriterionService {
   @InjectMocks
   CriterionService criterionService;
   @Mock
   CriterionData criterionData;

   @Nested
   class WithABooleanQuestion {
      @Mock
      YesNoQuestionData questionData;

      YesNoQuestion question = new YesNoQuestion(1, "?");

      @BeforeEach
      public void create() {
         when(questionData.get(question.id())).thenReturn(question);
         criterionService = new CriterionService(questionData, criterionData);
      }

      @Test
      void isMetByAnswerMatchingItsExpectedAnswer() {
         var criterion = new Criterion(1, question.id(), Yes);

         assertTrue(criterionService.isMetBy(criterion, Yes));
      }

      @Test
      void isNotMetByAnswerMismatchingItsExpectedAnswer() {
         var criterion = new Criterion(1, question.id(), Yes);

         assertFalse(criterionService.isMetBy(criterion, No));
      }
   }
   
   @Nested
   class GetQuestion {
      YesNoQuestion question = new YesNoQuestion(42, "When?");
      Criterion criterion = new Criterion(1, question.id(), Yes);

      @InjectMocks
      CriterionService aCriterionService;

      @Mock
      CriterionData criterionData;
      @Mock
      YesNoQuestionData questionData;

      @Test
      void returnsQuestionAssociatedWithId() {
         when(criterionData.get(criterion.id())).thenReturn(criterion);
         when(questionData.get(criterion.questionId())).thenReturn(question);

         var retrieved = aCriterionService.getQuestion(criterion.id());

         assertEquals(question, retrieved);
      }
   }

}