package iloveyouboss.domain.questions;

import iloveyouboss.domain.Question;

import java.time.Instant;
import java.util.List;
import static iloveyouboss.data.Data.NOT_PERSISTED_ID;
import static java.util.Arrays.asList;

public record YesNoQuestion(int id, String text, Instant timestamp) implements Question {
   public static final String Yes = "Yes";
   public static final String No = "No";

   public YesNoQuestion(int id, String text) {
      this(id, text, Instant.now());
   }
   public YesNoQuestion(String text, Instant timestamp) {
      this(NOT_PERSISTED_ID, text, timestamp);
   }
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
}
