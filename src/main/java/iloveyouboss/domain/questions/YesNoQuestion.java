package iloveyouboss.domain.questions;

import iloveyouboss.domain.Question;

import java.util.List;

import static iloveyouboss.data.Data.NOT_PERSISTED_ID;
import static java.util.Arrays.asList;

// START:record
public record YesNoQuestion(int id, String text) implements Question {
// END:record
   public static final String Yes = "Yes";
   public static final String No = "No";

   public YesNoQuestion(String text) {
      this(NOT_PERSISTED_ID, text);
   }

   public YesNoQuestion(int id, YesNoQuestion question) {
      this(id, question.text());
   }

   @Override
   public List<String> answerOptions() {
      return asList(Yes, No);
   }
// START:record
}
// END:record
