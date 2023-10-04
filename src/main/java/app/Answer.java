package app;

import static app.Data.NOT_PERSISTED_ID;

public record Answer(int id, int criterionId, String text) {
   public Answer(int criterionId, String text) {
      this(NOT_PERSISTED_ID, criterionId, text);
   }
}