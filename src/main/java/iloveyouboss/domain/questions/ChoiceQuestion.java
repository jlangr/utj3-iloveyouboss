package iloveyouboss.domain.questions;

import iloveyouboss.domain.Question;

import java.util.List;

public record ChoiceQuestion(int id, String text, List<String> options) implements Question {
}
