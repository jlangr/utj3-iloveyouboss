package iloveyouboss.data;

import iloveyouboss.data.CriterionData;
import iloveyouboss.domain.Criterion;
import iloveyouboss.domain.Question;
import iloveyouboss.domain.questions.YesNoQuestion;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static iloveyouboss.domain.questions.YesNoQuestion.No;
import static iloveyouboss.domain.questions.YesNoQuestion.Yes;
import static org.junit.jupiter.api.Assertions.*;

class ACriterionData {
   CriterionData criterionData = new CriterionData();
   Question question1 = new YesNoQuestion(100, "a");
   Question question2 = new YesNoQuestion(101, "b");

   @BeforeAll
   static void createTable() {
      new CriterionData().createIfNotExists();
   }

   @BeforeEach
   void reset() {
      criterionData.deleteAll();
   }

   @Test
   void isEmptyAfterReset() {
      assertEquals(0, criterionData.getAll().size());
   }

   @Test
   void retrievesInsertedRows() {
      var criterion1 = new Criterion(question1.id(), Yes);
      var criterion2 = new Criterion(question2.id(), No);
      var id1 = criterionData.add(criterion1);
      var id2 = criterionData.add(criterion2);

      var allRows = criterionData.getAll();

      assertEquals(
         List.of(new Criterion(id1, criterion1), new Criterion(id2, criterion2)),
         allRows);
   }

   @Test
   void retrievesRowById() {
      var id1 = criterionData.add(new Criterion(question1.id(), Yes));
      criterionData.add(new Criterion(question2.id(), No));

      var retrieved = criterionData.get(id1);

      assertEquals(Yes, retrieved.expectedAnswer());
   }
}