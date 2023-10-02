package iloveyouboss.answers;

import iloveyouboss.CriterionService;
import iloveyouboss.questions.yesno.YesNoQuestion;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static iloveyouboss.questions.yesno.YesNoQuestion.No;
import static iloveyouboss.questions.yesno.YesNoQuestion.Yes;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AnAnswerService {
   @InjectMocks
   AnswerService answerService;

   @Mock
   CriterionService criterionService;
   @Mock
   AnswerData answerData;

   static final int CRITERION_ID = 200;

   @Test
   void retrieveAllAnnotated() {
      var question = new YesNoQuestion(1, "Why?");
      var answer1 = new Answer(1, CRITERION_ID, Yes);
      var answer2 = new Answer(2, CRITERION_ID, No);
      when(criterionService.getQuestion(CRITERION_ID)).thenReturn(question);
      when(answerData.getAll()).thenReturn(List.of(answer1, answer2));

     var annotatedAnswers = answerService.retrieveAllAnnotated();

     assertEquals(List.of(
        new AnnotatedAnswer(answer1, question.text()),
        new AnnotatedAnswer(answer2, question.text())
     ), annotatedAnswers);
   }
}