package iloveyouboss.service;

import iloveyouboss.data.QuestionData;
import iloveyouboss.data.YesNoQuestionData;
import iloveyouboss.domain.Criteria;
import iloveyouboss.domain.Criterion;
import iloveyouboss.domain.DuplicateQuestionException;
import iloveyouboss.domain.questions.YesNoQuestion;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static iloveyouboss.domain.Question.AnswerNotProvided;
import static iloveyouboss.domain.questions.YesNoQuestion.No;
import static iloveyouboss.domain.questions.YesNoQuestion.Yes;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AProfileService {
   @InjectMocks
   ProfileService profile;

   @Mock
   QuestionData questionData;

   YesNoQuestion hasRelo = new YesNoQuestion(1, "Has relocation package?");
   YesNoQuestion has401K = new YesNoQuestion(2, "Has 401K?");
   YesNoQuestion hasSmelt = new YesNoQuestion(3, "got smelt?");

   Criterion mustHaveRelo = new Criterion(1, hasRelo.id(), Yes);
   Criterion mustHave401K = new Criterion(2, has401K.id(), Yes);
   Criterion optionallyHasSmeltShouldBeTrue =
      new Criterion(3, hasSmelt.id(), Yes, true);

   @Nested
   class WhenDeterminingMatches {
      @Test
      void doesNotMatchWhenProfileHasNoAnswerForCriterion() {
         var criteria = new Criteria(new Criterion(1, hasRelo.id(), Yes));

         assertFalse(profile.matches(criteria));
      }

      @Test
      void doesNotMatchWhenAllCriteriaNotMet() {
         when(questionData.get(hasRelo.id())).thenReturn(hasRelo);
         when(questionData.get(has401K.id())).thenReturn(has401K);
         profile.answer(hasRelo, Yes);
         profile.answer(has401K, No);

         var matches = profile.matches(new Criteria(mustHaveRelo, mustHave401K));

         assertFalse(matches);
      }

      @Nested
      class WithAllQuestionsAnsweredYes {
         @Test
         void matchesWhenAllCriteriaMet() {
            when(questionData.get(hasRelo.id())).thenReturn(hasRelo);
            when(questionData.get(has401K.id())).thenReturn(has401K);
            profile.answer(hasRelo, Yes);
            profile.answer(has401K, Yes);

            var matches = profile.matches(new Criteria(mustHaveRelo, mustHave401K));

            assertTrue(matches);
         }

         @Test
         void matchesDespiteUnmetOptionalCriterion() {
            when(questionData.get(hasRelo.id())).thenReturn(hasRelo);
            var optionalCriterion = new Criterion(3, hasSmelt.id(), Yes, true);
            var criteria = new Criteria(mustHaveRelo, optionalCriterion);
            profile.answer(hasSmelt, No);
            profile.answer(hasRelo, Yes);

            var matches = profile.matches(criteria);

            assertTrue(matches);
         }

         @Test
         void stillMatchesWithOnlyMismatchedOptionalCriteria() {
            var criteria = new Criteria(optionallyHasSmeltShouldBeTrue);
            profile.answer(hasSmelt, No);

            assertTrue(profile.matches(criteria));
         }
      }
   }

   @Nested
   class WhenManagingAnswers {
      @Test
      void returnsNullWhenAskedForNonexistentAnswer() {
         assertSame(AnswerNotProvided, profile.answerFor(mustHave401K));
      }

      @Test
      void returnsAnswerForCorrespondingCriterionQuestion() {
         profile.answer(has401K, Yes);
         var criterion = new Criterion(2, has401K.id(), Yes);

         var answer = profile.answerFor(criterion);

         assertEquals(answer, Yes);
      }

      @Test
      void throwsWhenAddingDuplicateAnswer() {
         profile.answer(has401K, Yes);
         var questionWithDuplicateId = new YesNoQuestion(has401K.id(), "?");

         assertThrows(DuplicateQuestionException.class,
            () -> profile.answer(questionWithDuplicateId, No));
      }
   }
}