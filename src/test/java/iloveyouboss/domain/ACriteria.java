package iloveyouboss.domain;

import iloveyouboss.domain.Criteria;
import iloveyouboss.domain.Criterion;
import org.junit.jupiter.api.Test;
import java.util.List;

import static iloveyouboss.domain.questions.YesNoQuestion.No;
import static iloveyouboss.domain.questions.YesNoQuestion.Yes;
import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ACriteria {
   int questionId = 100;
   Criterion criterion1 = new Criterion(1, questionId, Yes);
   Criterion criterion2 = new Criterion(2, questionId, No);
   Criterion criterion3 = new Criterion(3, questionId, No);

   @Test
   void holdsACollectionOfCriterion() {
      var criteria = new Criteria(List.of(criterion1, criterion2));

      assertEquals(listOfCriterion(criteria), List.of(criterion1, criterion2));
   }

   @Test
   void canBeConstructedWithVarargs() {
      var criteria = new Criteria(criterion1, criterion2);

      assertEquals(listOfCriterion(criteria), List.of(criterion1, criterion2));
   }

   @Test
   void canAcceptAdditionalCriterion() {
      var criteria = new Criteria(criterion1);

      criteria.add(criterion2);
      criteria.add(criterion3);

      assertEquals(listOfCriterion(criteria), List.of(criterion1, criterion2, criterion3));
   }

   private static List<Criterion> listOfCriterion(Criteria criteria) {
      return criteria.stream().collect(toList());
   }
}