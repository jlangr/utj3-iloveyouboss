package iloveyouboss.domain;

import java.util.List;

public interface Question {
   int id();
   List<String> answerOptions();
   String text();

   String AnswerNotProvided = "AnswerNotProvided";
}
