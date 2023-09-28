package iloveyouboss.questions;

import iloveyouboss.questions.yesno.YesNoQuestion;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AQuestionData {
   QuestionData questionData = new QuestionData();

   @BeforeAll
   static void createTable() {
      new QuestionData().create();
   }

   @BeforeEach
   void reset() {
      questionData.deleteAll();
      questionData.resetId();
   }

   @Test
   void isEmptyAfterReset() {
      assertEquals(0, questionData.getAll().size());
   }

   @Test
   void retrievesInsertedRows() {
      var question1 = new YesNoQuestion(1, "a");
      var question2 = new YesNoQuestion(2, "b");
      questionData.add(question1);
      questionData.add(question2);

      var allRows = questionData.getAll();

      assertEquals(List.of(question1, question2), allRows);
   }

   @Test
   void retrievesRowById() {
      var question = new YesNoQuestion(42, "a");
      questionData.add(question);

      var retrieved = questionData.get(42);

      assertEquals(question, retrieved);
   }
}