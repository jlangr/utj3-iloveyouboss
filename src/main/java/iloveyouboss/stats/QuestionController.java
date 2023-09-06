package iloveyouboss.stats;

import iloveyouboss.questions.Question;
import iloveyouboss.questions.yesno.YesNoQuestion;

class QuestionController {
   static Question q1 = new YesNoQuestion(1, "Tuition reimbursement?");
   static Question q2 = new YesNoQuestion(2, "Relocation package?");

   Question find(int id) {
      return id == 1 ? q1 : q2;
   }
}
