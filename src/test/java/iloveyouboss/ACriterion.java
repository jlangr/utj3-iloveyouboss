package iloveyouboss;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;

class ACriterion {
   @Test
   void defaultsIsOptionalToFalse() {
      assertFalse(new Criterion(1, 100, "x").isOptional());
   }
}
