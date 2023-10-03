package iloveyouboss.utils;

import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class StringUtils {
   public static List<String> fromCSV(String csv) {
      return Stream.of(csv.split(",")).collect(toList());
   }

   public static String toCSV(List<String> answerOptions) {
      return String.join(",", answerOptions);
   }
}
