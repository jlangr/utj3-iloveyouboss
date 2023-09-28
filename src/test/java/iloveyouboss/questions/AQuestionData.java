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
      new QuestionData().createIfNotExists();
   }

   @BeforeEach
   void reset() {
      questionData.deleteAll();
   }

   @Test
   void isEmptyAfterReset() {
      assertEquals(0, questionData.getAll().size());
   }

   @Test
   void retrievesInsertedRows() {
      var question1 = new YesNoQuestion("a");
      var question2 = new YesNoQuestion("b");
      var id1 = questionData.add(question1);
      var id2 = questionData.add(question2);

      var allRows = questionData.getAll();

      assertEquals(
         List.of(new YesNoQuestion(id1, question1), new YesNoQuestion(id2, question2)),
         allRows);
   }

   @Test
   void retrievesRowById() {
      var id1 = questionData.add(new YesNoQuestion("a"));
      questionData.add(new YesNoQuestion("b"));

      var retrieved = questionData.get(id1);

      assertEquals("a", retrieved.text());
   }
}
