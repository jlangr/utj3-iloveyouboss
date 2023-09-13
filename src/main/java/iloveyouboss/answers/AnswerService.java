package iloveyouboss.answers;

import iloveyouboss.questions.QuestionService;

import java.util.List;

import static java.util.stream.Collectors.*;

public class AnswerService {
   private AnswersPersistence answersPersistence = new AnswersPersistence();
   private QuestionService questionService = new QuestionService();

   public List<Answer> retrieveAll() {
      return answersPersistence.retrieveAll();
   }

   public List<AnnotatedAnswer> retrieveAllAnnotated() {
      return answersPersistence.retrieveAll()
         .stream()
         .map(this::createAnnotatedAnswer)
         .collect(toList());
   }

   private AnnotatedAnswer createAnnotatedAnswer(Answer answer) {
      return new AnnotatedAnswer(answer, questionService.get(answer.questionId()).text());
   }
}
