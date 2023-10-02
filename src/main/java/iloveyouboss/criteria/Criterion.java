package iloveyouboss.criteria;

public record Criterion(int id, int questionId, String expectedAnswer, boolean isOptional) {
   public Criterion(int id, int questionId, String expectedAnswer) {
      this(id, questionId, expectedAnswer, false);
   }
}
