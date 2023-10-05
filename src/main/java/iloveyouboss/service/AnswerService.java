package iloveyouboss.service;

import iloveyouboss.data.AnswerData;
import iloveyouboss.domain.Answer;

import java.util.ArrayList;
import java.util.List;

public class AnswerService {
   private final AnswerData answerData;

   public AnswerService(AnswerData answerData) {
      this.answerData = answerData;
   }

   public List<Answer> retrieveAll() {
      return new ArrayList<>(answerData.getAll());
   }
}
