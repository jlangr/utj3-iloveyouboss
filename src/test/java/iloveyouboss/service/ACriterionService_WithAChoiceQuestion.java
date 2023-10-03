package iloveyouboss.service;

import iloveyouboss.data.ChoiceQuestionData;
import iloveyouboss.data.CriterionData;
import iloveyouboss.domain.Criterion;
import iloveyouboss.domain.InvalidAnswerException;
import iloveyouboss.domain.questions.ChoiceQuestion;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ACriterionService_WithAChoiceQuestion {
   @InjectMocks
   ACriterionService aCriterionService;

   @Mock
   ChoiceQuestionData questionData;

   ChoiceQuestion question = new ChoiceQuestion(1, "?", List.of("eeny", "meeny", "miny", "moe"));

   @BeforeEach
   public void create() {
      when(questionData.get(question.id())).thenReturn(question);
      aCriterionService.criterionService = new CriterionService(questionData, aCriterionService.criterionData);
   }

   @Test
   void isMetByAnswerMatchingItsExpectedAnswer() {
      var criterion = new Criterion(question.id(), "eeny");

      Assertions.assertTrue(aCriterionService.criterionService.isMetBy(criterion, "eeny"));
   }

   @Test
   void isNotMetByAnswerMismatchingItsExpectedAnswer() {
      var criterion = new Criterion(question.id(), "meeny");

      Assertions.assertFalse(aCriterionService.criterionService.isMetBy(criterion, "moe"));
   }

   @Test
   void throwsWhenAnswerDoesNotMatchAvailableChoices() {
      var criterion = new Criterion(
         new ChoiceQuestion(1, "?", Collections.singletonList("correct")).id(),
         "correct");
      var answerOutOfRange = "anything else";

      assertThrows(InvalidAnswerException.class,
         () -> aCriterionService.criterionService.isMetBy(criterion, answerOutOfRange));
   }
}
