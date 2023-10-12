package iloveyouboss.domain;

import iloveyouboss.domain.questions.YesNoQuestion;
import iloveyouboss.service.CriterionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Map;

import static iloveyouboss.domain.questions.YesNoQuestion.No;
import static iloveyouboss.domain.questions.YesNoQuestion.Yes;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AStatisticsCompiler {
   @InjectMocks
   StatisticsCompiler compiler;
   @Mock
   CriterionService criterionService;

   Question hasTuitionReimburse = new YesNoQuestion(1, "Tuition?");
   Question hasRelocation = new YesNoQuestion(2, "Relocation?");
   Criterion tuitionCriterion = new Criterion(1, hasTuitionReimburse.id(), Yes);
   Criterion relocationCriterion = new Criterion(2, hasRelocation.id(), Yes);

   @Test
   void producesAnswerCountsByQuestionTextHistogram() {
      when(criterionService.getQuestion(1)).thenReturn(hasTuitionReimburse);
      when(criterionService.getQuestion(2)).thenReturn(hasRelocation);
      var answers = List.of(
         new Answer(1, tuitionCriterion.id(), Yes),
         new Answer(2, tuitionCriterion.id(), Yes),
         new Answer(3, tuitionCriterion.id(), Yes),
         new Answer(4, tuitionCriterion.id(), No),
         new Answer(5, relocationCriterion.id(), Yes),
         new Answer(6, relocationCriterion.id(), Yes));

      var statistics = compiler.answerCountsByQuestionText(answers);

      assertEquals(
         Map.of(
            hasTuitionReimburse.text(), Map.of(Yes, 3, No, 1),
            hasRelocation.text(), Map.of(Yes, 2, No, 0)),
         statistics);
   }
}
