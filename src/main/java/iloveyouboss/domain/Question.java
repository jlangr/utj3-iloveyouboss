// START:interface
package iloveyouboss.domain;

import iloveyouboss.database.Nullable;

import java.util.List;

public interface Question {
   int id();
   default String type() {
      return getClass().getSimpleName();
   }
   String text();
   @Nullable List<String> answerOptions();
// END:interface

   String AnswerNotProvided = "AnswerNotProvided";
// START:interface
}
// END:interface
