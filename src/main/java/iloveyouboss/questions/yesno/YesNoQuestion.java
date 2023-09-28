package iloveyouboss.questions.yesno;

import iloveyouboss.questions.Question;

import java.util.List;

import static java.util.Arrays.asList;

public record YesNoQuestion(int id, String text) implements Question {
   public static final String Yes = "Yes";
   public static final String No = "No";
   private static final int NOT_PERSISTED_ID = -1;

   public YesNoQuestion(String text) {
      this(NOT_PERSISTED_ID, text);
   }

   public YesNoQuestion(int id, YesNoQuestion question) {
      this(id, question.text());
   }

   @Override
   public List<String> options() {
      return asList(Yes, No);
   }
}
