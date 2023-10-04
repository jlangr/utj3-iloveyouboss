package app;

import java.util.List;

// START:interface
public interface Question {
   int id();
   List<String> answerOptions();
   String text();
// END:interface

   String AnswerNotProvided = "AnswerNotProvided";
// START:interface
}
// END:interface
