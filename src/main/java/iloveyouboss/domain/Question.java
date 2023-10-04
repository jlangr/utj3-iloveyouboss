// START:interface
package iloveyouboss.domain;

import java.util.List;

public interface Question {
   int id();
   List<String> answerOptions();
   String text();
// END:interface

   String AnswerNotProvided = "AnswerNotProvided";
// START:interface
}
// END:interface
