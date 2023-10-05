package iloveyouboss.domain;

import iloveyouboss.data.CriterionData;
import iloveyouboss.data.QuestionData;
import iloveyouboss.domain.questions.YesNoQuestion;
import iloveyouboss.service.CriterionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

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

   YesNoQuestion tuitionQuestion = new YesNoQuestion(1, "Tuition reimbursement?");
   YesNoQuestion relocationQuestion = new YesNoQuestion(2, "Relocation package?");
   Criterion tuitionCriterion = new Criterion(1, tuitionQuestion.id(), Yes);;
   Criterion relocationCriterion = new Criterion(2, relocationQuestion.id(), Yes);

   @Test
   void producesAnswerCountsByQuestionTextHistogram() {
      when(criterionService.getQuestion(1)).thenReturn(tuitionQuestion);
      when(criterionService.getQuestion(2)).thenReturn(relocationQuestion);
      var answers = List.of(
         new Answer(1, tuitionCriterion.id(), Yes),
         new Answer(2, tuitionCriterion.id(), Yes),
         new Answer(3, tuitionCriterion.id(), Yes),
         new Answer(4, tuitionCriterion.id(), No),
         new Answer(5, relocationCriterion.id(), Yes),
         new Answer(6, relocationCriterion.id(), Yes));

      var statistics = compiler.answerCountsByQuestionText(answers);

      assertEquals(3, statistics.get(tuitionQuestion.text()).get(Yes));
      assertEquals(1, statistics.get(tuitionQuestion.text()).get(No));
      assertEquals(2, statistics.get(relocationQuestion.text()).get(Yes));
      assertEquals(0, statistics.get(relocationQuestion.text()).get(No));
   }
}
