package iloveyouboss.criteria;

import static iloveyouboss.data.Data.NOT_PERSISTED_ID;

public record Criterion(int id, int questionId, String expectedAnswer, boolean isOptional) {
   public Criterion(int id, int questionId, String expectedAnswer) {
      this(id, questionId, expectedAnswer, false);
   }

   public Criterion(int questionId, String expectedAnswer, boolean isOptional) {
      this(NOT_PERSISTED_ID, questionId, expectedAnswer, isOptional);
   }
   public Criterion(int questionId, String expectedAnswer) {
      this(NOT_PERSISTED_ID, questionId, expectedAnswer, false);
   }
   public Criterion(int id, Criterion criterion) {
      this(id, criterion.questionId(), criterion.expectedAnswer(), criterion.isOptional());
   }
}
