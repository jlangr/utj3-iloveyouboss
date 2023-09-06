package iloveyouboss.stats;

import iloveyouboss.questions.Question;

class QuestionController {
   Question find(int id) {
      return id == 1 ? StatisticsCompiler.q1 : StatisticsCompiler.q2;
   }
}
