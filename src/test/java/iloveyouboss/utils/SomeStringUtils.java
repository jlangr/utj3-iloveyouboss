package iloveyouboss.utils;

import org.junit.jupiter.api.Test;

import java.util.List;

import static iloveyouboss.utils.StringUtils.fromCSV;
import static iloveyouboss.utils.StringUtils.toCSV;
import static org.junit.jupiter.api.Assertions.assertEquals;

class SomeStringUtils {
   @Test
   void createListOfStringsFromCSV() {
      assertEquals(List.of("alpha", "beta"), fromCSV("alpha,beta"));
   }

   @Test
   void createCSVFromListOfStrings() {
      assertEquals("alpha,beta", toCSV(List.of("alpha", "beta")));
   }
}
