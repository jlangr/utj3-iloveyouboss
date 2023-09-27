package iloveyouboss.answers;

import iloveyouboss.questions.QuestionData;

import java.util.List;

import static java.util.stream.Collectors.toList;

// TODO test
public class AnswerService {
   private AnswersPersistence answersPersistence = new AnswersPersistence();
   private QuestionData questionData = new QuestionData();
   // TODO need mock

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
      return new AnnotatedAnswer(answer, questionData.get(answer.questionId()).text());
   }
}
