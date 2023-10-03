package iloveyouboss.domain.questions;

import org.junit.jupiter.api.Test;

import java.util.List;

import static iloveyouboss.domain.questions.YesNoQuestion.No;
import static iloveyouboss.domain.questions.YesNoQuestion.Yes;
import static org.junit.jupiter.api.Assertions.*;

class AYesNoQuestion {
   @Test
   void answersYesAndNoForOptions() {
      assertEquals(List.of(Yes, No), new YesNoQuestion(1, "").answerOptions());
   }
}