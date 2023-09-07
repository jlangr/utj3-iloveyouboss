package iloveyouboss;

import iloveyouboss.questions.Question;

public record Answer(Criterion criterion, String value) {
   public Question question() {
      return criterion().question();
   }
}