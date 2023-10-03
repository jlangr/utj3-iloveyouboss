package iloveyouboss.data;

import iloveyouboss.domain.questions.ChoiceQuestion;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AChoiceQuestionData {
   ChoiceQuestionData questionData = new ChoiceQuestionData();
   ChoiceQuestion questionA = new ChoiceQuestion("a", List.of("1", "3"));
   ChoiceQuestion questionB = new ChoiceQuestion("b", List.of("2", "4"));

   @BeforeAll
   static void createTable() {
      new ChoiceQuestionData().createIfNotExists();
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
      var idA = questionData.add(questionA);
      var idB = questionData.add(questionB);

      var allRows = questionData.getAll();

      assertEquals(
         List.of(new ChoiceQuestion(idA, questionA), new ChoiceQuestion(idB, questionB)),
         allRows);
   }

   @Test
   void retrievesRowById() {
      var idA = questionData.add(questionA);
      questionData.add(questionB);

      var retrieved = questionData.get(idA);

      assertEquals("a", retrieved.text());
   }
}