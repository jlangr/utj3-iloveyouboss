package iloveyouboss.service;

import iloveyouboss.data.CriterionData;
import iloveyouboss.data.YesNoQuestionData;
import iloveyouboss.domain.Criterion;
import iloveyouboss.domain.questions.YesNoQuestion;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static iloveyouboss.domain.questions.YesNoQuestion.Yes;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ACriterionService {
   @InjectMocks
   CriterionService criterionService;

   @Mock
   YesNoQuestionData questionData;
   @Mock
   CriterionData criterionData;

   @Nested
   class GetQuestion {
      YesNoQuestion question = new YesNoQuestion(42, "When?");
      Criterion criterion = new Criterion(1, question.id(), Yes);

      // TODO why does this intermittently fail
      @Test
      void returnsQuestionAssociatedWithId() {
         when(criterionData.get(criterion.id())).thenReturn(criterion);
         when(questionData.get(criterion.questionId())).thenReturn(question);

         var retrieved = criterionService.getQuestion(criterion.id());

         assertEquals(question, retrieved);
      }
   }

}