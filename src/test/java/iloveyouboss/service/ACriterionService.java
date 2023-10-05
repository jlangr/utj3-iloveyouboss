package iloveyouboss.service;

import iloveyouboss.data.ChoiceQuestionData;
import iloveyouboss.data.QuestionData;
import iloveyouboss.data.YesNoQuestionData;
import iloveyouboss.domain.Criterion;
import iloveyouboss.domain.InvalidAnswerException;
import iloveyouboss.domain.questions.ChoiceQuestion;
import iloveyouboss.domain.questions.YesNoQuestion;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static iloveyouboss.domain.questions.YesNoQuestion.No;
import static iloveyouboss.domain.questions.YesNoQuestion.Yes;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ACriterionService {
   @Nested
   class GivenYesNoCriterion {
      @InjectMocks
      CriterionService criterionService;

      @Mock
      QuestionData questionData;

      YesNoQuestion question = new YesNoQuestion(1, "?");
      Criterion yesCriterion = new Criterion(1, question.id(), Yes);

      @BeforeEach
      public void create() {
         when(questionData.get(question.id())).thenReturn(question);
      }

      @Test
      void isMetByAnswerMatchingItsExpectedAnswer() {
         assertTrue(criterionService.isMetBy(yesCriterion, Yes));
      }

      @Test
      void isNotMetByAnswerMismatchingItsExpectedAnswer() {
         assertFalse(criterionService.isMetBy(yesCriterion, No));
      }
   }

   @Nested
   class GivenChoiceQuestion {
      @InjectMocks
      CriterionService criterionService;

      @Mock
      QuestionData questionData;

      ChoiceQuestion question =
         new ChoiceQuestion(1, "?", List.of("eeny", "meeny", "miny", "moe"));
      Criterion eenyCriterion = new Criterion(question.id(), "eeny");

      @BeforeEach
      public void create() {
         when(questionData.get(question.id())).thenReturn(question);
      }

      @Test
      void isMetByAnswerMatchingItsExpectedAnswer() {
         assertTrue(criterionService.isMetBy(eenyCriterion, "eeny"));
      }

      @Test
      void isNotMetByAnswerMismatchingItsExpectedAnswer() {
         assertFalse(criterionService.isMetBy(eenyCriterion, "moe"));
      }

      @Test
      void throwsWhenAnswerDoesNotMatchAvailableChoices() {
         assertThrows(InvalidAnswerException.class,
            () -> criterionService.isMetBy(eenyCriterion, "invalid answer"));
      }
   }
}
