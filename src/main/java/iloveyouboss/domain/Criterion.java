// START:record
package iloveyouboss.domain;

// END:record
import static iloveyouboss.data.Data.NOT_PERSISTED_ID;

// START:record
public record Criterion(int id, int questionId, String expectedAnswer, boolean isOptional) {
// END:record
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
// START:record
}
// END:record
