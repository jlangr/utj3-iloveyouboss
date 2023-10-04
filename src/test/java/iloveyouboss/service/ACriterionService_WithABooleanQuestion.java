package iloveyouboss.service;

import iloveyouboss.data.YesNoQuestionData;
import iloveyouboss.domain.Criterion;
import iloveyouboss.domain.questions.YesNoQuestion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static iloveyouboss.domain.questions.YesNoQuestion.No;
import static iloveyouboss.domain.questions.YesNoQuestion.Yes;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ACriterionService_WithABooleanQuestion {
   @InjectMocks
   CriterionService criterionService;

   @Mock
   YesNoQuestionData questionData;

   YesNoQuestion question = new YesNoQuestion(1, "?");

   @BeforeEach
   public void create() {
      when(questionData.get(question.id())).thenReturn(question);
   }

//   @Test
//   void isMetByAnswerMatchingItsExpectedAnswer() {
//      var criterion = new Criterion(1, question.id(), Yes);
//
//      assertTrue(criterionService.isMetBy(criterion, Yes));
//   }

   @Test
   void isNotMetByAnswerMismatchingItsExpectedAnswer() {
      var criterion = new Criterion(1, question.id(), Yes);

      assertFalse(criterionService.isMetBy(criterion, No));
   }
}
