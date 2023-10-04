package iloveyouboss.service;

import iloveyouboss.data.AnswerData;
import iloveyouboss.domain.AnnotatedAnswer;
import iloveyouboss.domain.Answer;
import iloveyouboss.service.AnswerService;
import iloveyouboss.service.CriterionService;
import iloveyouboss.domain.questions.YesNoQuestion;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static iloveyouboss.domain.questions.YesNoQuestion.No;
import static iloveyouboss.domain.questions.YesNoQuestion.Yes;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AnAnswerService {
   @InjectMocks
   AnswerService answerService;
   @Mock
   CriterionService criterionService;
   @Mock
   AnswerData answerData;

   int criterionId = 200;

   @Test
   void retrieveAllAnnotated() {
      var question = new YesNoQuestion(1, "Why?");
      var answer1 = new Answer(1, criterionId, Yes);
      var answer2 = new Answer(2, criterionId, No);
      when(criterionService.getQuestion(criterionId)).thenReturn(question);
      when(answerData.getAll()).thenReturn(List.of(answer1, answer2));

     var annotatedAnswers = answerService.retrieveAllAnnotated();

     assertEquals(List.of(
        new AnnotatedAnswer(answer1, question.text()),
        new AnnotatedAnswer(answer2, question.text())
     ), annotatedAnswers);
   }
}