package iloveyouboss;

public record Criterion(int questionId, String expectedAnswer, boolean isOptional) {
   public Criterion(int questionId, String expectedAnswer) {
      this(questionId, expectedAnswer, false);
   }
}
