package iloveyouboss.domain.questions;

import iloveyouboss.domain.Question;

import java.util.List;

import static iloveyouboss.data.Data.NOT_PERSISTED_ID;

public record ChoiceQuestion(int id, String text, List<String> answerOptions) implements Question {
   public ChoiceQuestion(String text, List<String> answerOptions) {
      this(NOT_PERSISTED_ID, text, answerOptions);
   }

   public ChoiceQuestion(int id, ChoiceQuestion question) {
      this(id, question.text(), question.answerOptions());
   }
}
