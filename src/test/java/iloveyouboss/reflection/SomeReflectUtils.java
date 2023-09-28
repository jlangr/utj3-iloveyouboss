package iloveyouboss.reflection;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static iloveyouboss.reflection.ReflectUtils.field;
import static iloveyouboss.reflection.ReflectUtils.type;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SomeReflectUtils {
   @Nested
   class Type {
      @Test
      void forFieldInRecord() {
         record X(int count) {}

         assertEquals(int.class, type("count", X.class));
      }

      @Test
      void forPrivateFieldInClass() {
         class Y { private String name; }

         assertEquals(String.class, type("name", Y.class));
      }
   }

   @Nested
   class Field {
      class Z { private String title; }

      @Test
      void forColumnInClass() {
         var field = field("title", Z.class);

         assertEquals("title", field.getName());
         assertEquals(Z.class, field.getDeclaringClass());
      }

      @Test
      void throwsWhenFieldDoesNotExist() {
         assertThrows(RuntimeException.class, () -> field("nope", Z.class));
      }
   }
}