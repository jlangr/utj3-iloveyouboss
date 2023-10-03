package iloveyouboss;

import iloveyouboss.criteria.CriterionData;
import iloveyouboss.criteria.CriterionService;
import iloveyouboss.questions.Question;
import iloveyouboss.questions.YesNoQuestionData;
import iloveyouboss.questions.yesno.YesNoQuestion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static iloveyouboss.questions.yesno.YesNoQuestion.No;
import static iloveyouboss.questions.yesno.YesNoQuestion.Yes;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ACriterionService {
   @InjectMocks
   CriterionService criterionService;
   @Mock
   CriterionData criterionData;
   @Mock
   YesNoQuestionData questionData;

   @Nested
   class WithABooleanQuestion {
      YesNoQuestion question = new YesNoQuestion(1, "?");

      @BeforeEach
      public void create() {
         when(questionData.get(question.id())).thenReturn(question);
         criterionService = new CriterionService(questionData, criterionData);
      }

      @Test
      void isMetByAnswerMatchingItsExpectedAnswer() {
         var criterion = new iloveyouboss.criteria.Criterion(1, question.id(), Yes);

         assertTrue(criterionService.isMetBy(criterion, Yes));
      }

      @Test
      void isNotMetByAnswerMismatchingItsExpectedAnswer() {
         var criterion = new iloveyouboss.criteria.Criterion(1, question.id(), Yes);

         assertFalse(criterionService.isMetBy(criterion, No));
      }
   }
   
   @Nested
   @ExtendWith(MockitoExtension.class)
   class GetQuestion {
      Question question = new YesNoQuestion(42, "When?");
      iloveyouboss.criteria.Criterion criterion = new iloveyouboss.criteria.Criterion(1, question.id(), Yes);

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

   // TODO resurrect

//   @Nested
//   class WithAChoiceQuestion {
//      ChoiceQuestion question = new ChoiceQuestion(1, "?", List.of("eeny", "meeny", "miny", "moe"));
//
//      @BeforeEach
//      public void create() {
//         var questionData = new QuestionData() {
//            @Override
//            public YesNoQuestion get(int id) {
//               return question;
//            }
//         };
//         criterionService = new CriterionService(questionData);
//      }
//
//      @Test
//      void isMetByAnswerMatchingItsExpectedAnswer() {
//         var criterion = new Criterion(question.id(), "eeny");
//
//         assertTrue(criterionService.isMetBy(criterion, "eeny"));
//      }
//
//      @Test
//      void isNotMetByAnswerMismatchingItsExpectedAnswer() {
//         var criterion = new Criterion(question.id(), "meeny");
//
//         assertFalse(criterionService.isMetBy(criterion, "moe"));
//      }
//
//      @Test
//      void throwsWhenAnswerDoesNotMatchAvailableChoices() {
//         var criterion = new Criterion(
//            new ChoiceQuestion(1, "?", Collections.singletonList("correct")).id(),
//            "correct");
//         var answerOutOfRange = "anything else";
//
//         assertThrows(InvalidAnswerException.class,
//            () -> criterionService.isMetBy(criterion, answerOutOfRange));
//      }
//   }

}