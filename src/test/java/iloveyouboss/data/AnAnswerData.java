package iloveyouboss.data;

import iloveyouboss.data.AnswerData;
import iloveyouboss.database.DB;
import iloveyouboss.domain.Answer;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

import static iloveyouboss.domain.questions.YesNoQuestion.No;
import static iloveyouboss.domain.questions.YesNoQuestion.Yes;
import static org.junit.jupiter.api.Assertions.*;

class AnAnswerData {
   AnswerData answerData = new AnswerData();

   @BeforeAll
   static void createTable() throws SQLException {
      DB.connection().createStatement().execute(
         "DROP TABLE IF EXISTS Answer");
      new AnswerData().createIfNotExists();
   }

   @BeforeEach
   void reset() {
      answerData.deleteAll();
   }

   @Test
   void isEmptyAfterReset() {
      assertEquals(0, answerData.getAll().size());
   }

   static final int CRITERION_ID = 100;

   @Test
   void retrievesInsertedRows() {
      var answer1 = new Answer(1, CRITERION_ID, Yes);
      var answer2 = new Answer(2, CRITERION_ID, No);
      var id1 = answerData.add(answer1);
      var id2 = answerData.add(answer2);

      var allRows = answerData.getAll();

      assertEquals(
         List.of(new Answer(id1, CRITERION_ID, Yes), new Answer(id2, CRITERION_ID, No)),
         allRows);
   }

   @Test
   void retrievesRowById() {
      var id1 = answerData.add(new Answer(1, CRITERION_ID, Yes));
      answerData.add(new Answer(2, CRITERION_ID, No));

      var retrieved = answerData.get(id1);

      assertEquals(Yes, retrieved.text());
   }
}