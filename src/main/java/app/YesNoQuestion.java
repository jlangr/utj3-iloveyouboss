package app;

import java.util.List;
import static java.util.Arrays.asList;

public record YesNoQuestion(int id, String text) implements Question {
   @Override
   public List<String> answerOptions() {
      return asList("Yes", "No");
   }
}
