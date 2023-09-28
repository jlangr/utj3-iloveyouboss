package iloveyouboss;

import iloveyouboss.questions.Question;
import iloveyouboss.questions.QuestionData;
import iloveyouboss.questions.choice.ChoiceQuestion;
import iloveyouboss.questions.yesno.YesNoQuestion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static iloveyouboss.questions.yesno.YesNoQuestion.No;
import static iloveyouboss.questions.yesno.YesNoQuestion.Yes;
import static org.junit.jupiter.api.Assertions.*;

class ACriterionService {
   CriterionService criterionService;

   @Nested
   class WithABooleanQuestion {
      YesNoQuestion question = new YesNoQuestion(1, "?");

      @BeforeEach
      public void create() {
         var questionData = new QuestionData() {
            @Override
            public Question get(int id) {
               return question;
            }
         };
         criterionService = new CriterionService(questionData);
      }

      @Test
      void isMetByAnswerMatchingItsExpectedAnswer() {
         var criterion = new Criterion(question.id(), Yes);

         assertTrue(criterionService.isMetBy(criterion, Yes));
      }

      @Test
      void isNotMetByAnswerMismatchingItsExpectedAnswer() {
         var criterion = new Criterion(question.id(), Yes);

         assertFalse(criterionService.isMetBy(criterion, No));
      }
   }

   @Nested
   class WithAChoiceQuestion {
      ChoiceQuestion question = new ChoiceQuestion(1, "?", List.of("eeny", "meeny", "miny", "moe"));

      @BeforeEach
      public void create() {
         var questionData = new QuestionData() {
            @Override
            public Question get(int id) {
               return question;
            }
         };
         criterionService = new CriterionService(questionData);
      }

      @Test
      void isMetByAnswerMatchingItsExpectedAnswer() {
         var criterion = new Criterion(question.id(), "eeny");

         assertTrue(criterionService.isMetBy(criterion, "eeny"));
      }

      @Test
      void isNotMetByAnswerMismatchingItsExpectedAnswer() {
         var criterion = new Criterion(question.id(), "meeny");

         assertFalse(criterionService.isMetBy(criterion, "moe"));
      }

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

}