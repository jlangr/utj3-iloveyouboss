package iloveyouboss;

public record Answer(Criterion criterion, String value) {
   public int questionId() {
      return criterion().question().id();
   }
}
