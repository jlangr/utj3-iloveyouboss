package iloveyouboss.data;

import iloveyouboss.domain.questions.ChoiceQuestion;
import iloveyouboss.domain.questions.YesNoQuestion;
import iloveyouboss.domain.utils.ComparisonUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static iloveyouboss.domain.utils.ComparisonUtils.withoutTimestamps;
import static org.junit.jupiter.api.Assertions.assertEquals;

class AQuestionData {
   QuestionData questionData = new QuestionData();
   YesNoQuestion yesNoQuestionA = new YesNoQuestion("A");
   YesNoQuestion yesNoQuestionB = new YesNoQuestion("B");
   ChoiceQuestion choiceQuestionC = new ChoiceQuestion("C", List.of("1", "3"));
   ChoiceQuestion choiceQuestionD = new ChoiceQuestion("D", List.of("2", "4"));

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
      var idYesNoA = questionData.add(yesNoQuestionA);
      var idYesNoB = questionData.add(yesNoQuestionB);
      var idChoiceC = questionData.add(choiceQuestionC);
      var idChoiceD = questionData.add(choiceQuestionD);

      var allRows = questionData.getAll();

      assertEquals(withoutTimestamps(List.of(
               new YesNoQuestion(idYesNoA, yesNoQuestionA),
               new YesNoQuestion(idYesNoB, yesNoQuestionB),
               new ChoiceQuestion(idChoiceC, choiceQuestionC),
               new ChoiceQuestion(idChoiceD, choiceQuestionD))),
         withoutTimestamps(allRows));
   }

   @Test
   void retrievesRowsById() {
      var idQuestionA = questionData.add(yesNoQuestionA);
      questionData.add(yesNoQuestionB);
      var idQuestionC = questionData.add(choiceQuestionC);

      assertEquals(yesNoQuestionA.text(), questionData.get(idQuestionA).text());
      assertEquals(choiceQuestionC.text(), questionData.get(idQuestionC).text());
   }
}
