package iloveyouboss.service;

import iloveyouboss.data.ChoiceQuestionData;
import iloveyouboss.domain.Criterion;
import iloveyouboss.domain.InvalidAnswerException;
import iloveyouboss.domain.questions.ChoiceQuestion;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ACriterionService_WithAChoiceQuestion {
   @InjectMocks
   CriterionService criterionService;

   @Mock
   ChoiceQuestionData questionData;

   ChoiceQuestion question = new ChoiceQuestion(1, "?", List.of("eeny", "meeny", "miny", "moe"));

   @BeforeEach
   public void create() {
      when(questionData.get(question.id())).thenReturn(question);
   }

   @Test
   void isMetByAnswerMatchingItsExpectedAnswer() {
      var criterion = new Criterion(question.id(), "eeny");

      Assertions.assertTrue(criterionService.isMetBy(criterion, "eeny"));
   }

//   @Test
//   void isNotMetByAnswerMismatchingItsExpectedAnswer() {
//      var criterion = new Criterion(question.id(), "meeny");
//
//      Assertions.assertFalse(criterionService.isMetBy(criterion, "moe"));
//   }

   @Test
   void throwsWhenAnswerDoesNotMatchAvailableChoices() {
      var criterion = new Criterion(
         new ChoiceQuestion(1, "?", Collections.singletonList("correct")).id(),
         "correct");
      var answerOutOfRange = "anything else";

      assertThrows(InvalidAnswerException.class,
         () -> criterionService.isMetBy(criterion, answerOutOfRange));
   }
}
