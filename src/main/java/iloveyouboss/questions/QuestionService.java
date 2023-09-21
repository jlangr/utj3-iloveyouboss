package iloveyouboss.questions;

import iloveyouboss.questions.yesno.YesNoQuestion;

// TODO test and rethink
public class QuestionService {
   static Question q1 = new YesNoQuestion(1, "Tuition reimbursement?");
   static Question q2 = new YesNoQuestion(2, "Relocation package?");

   public Question get(int id) {
      return id == 1 ? q1 : q2;
   }
}
