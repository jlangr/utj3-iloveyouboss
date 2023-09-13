package iloveyouboss.answers;

import iloveyouboss.Criterion;

public record Answer(Criterion criterion, String value) {
   public int questionId() {
      return criterion().questionId();
   }
}