package iloveyouboss.answers;

import iloveyouboss.Criterion;

public record Answer(int id, int criterionId, String value) {
//   public int questionId() {
//      return criterion().questionId();
//   }
}