package iloveyouboss.domain.utils;

import iloveyouboss.domain.Question;
import iloveyouboss.domain.questions.YesNoQuestion;

import java.time.Instant;
import java.util.List;

import static java.util.stream.Collectors.toList;

// well, things like AssertJ make this much easier
public class ComparisonUtils {
   public static Question zeroTimestamp(Question question) {
      // TODO
//      return switch (question) {
//         case YesNoQuestion q -> new YesNoQuestion(q.id(), q.text(), Instant.MIN);
//         case ChoiceQuestion q -> q;
//         default -> null;
//      };
      if (question.getClass() == YesNoQuestion.class)
         return new YesNoQuestion(question.id(), question.text(), Instant.MIN);
      return question;
   }

   public static List<Question> zeroedTimestamps(List<Question> list) {
      return list.stream()
         .map(ComparisonUtils::zeroTimestamp)
         .collect(toList());
   }
}
