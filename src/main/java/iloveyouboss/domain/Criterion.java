package iloveyouboss.domain;

public record Criterion(int id, int questionId, String expectedAnswer, boolean isOptional) {
}
