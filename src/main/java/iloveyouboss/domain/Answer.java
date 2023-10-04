// START:record
package iloveyouboss.domain;

// END:record
import static iloveyouboss.data.Data.NOT_PERSISTED_ID;

// START:record
public record Answer(int id, int criterionId, String text) {
   // END:record
   public Answer(int criterionId, String text) {
      this(NOT_PERSISTED_ID, criterionId, text);
   }
// START:record
}
// END:record
