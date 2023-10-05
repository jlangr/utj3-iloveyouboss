package iloveyouboss.utils;

import iloveyouboss.database.Nullable;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static iloveyouboss.utils.ReflectUtils.*;
import static org.junit.jupiter.api.Assertions.*;

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

   @Nested
   class AccessorType {
      interface Y { String description(); }

      @Test
      void forMethodDeclarationInInterface() {
         assertEquals(String.class, accessorType("description", Y.class));
      }
   }

   @Nested
   class RecordHasAnnotation {
      record R(int alpha, @Nullable int beta) {}

      @Retention(RetentionPolicy.RUNTIME)
      @Target({ ElementType.RECORD_COMPONENT})
      public @interface Fizz {}

      @Test
      void isTrueWhenAnnotationExists() {
         assertTrue(recordComponentHasAnnotation("beta", R.class, Nullable.class));
      }

      @Test
      void isFalseWhenAnnotationDoesNotExist() {
         assertFalse(recordComponentHasAnnotation("alpha", R.class, Nullable.class));
      }

      @Test
      void isFalseWhenWrongAnnotationExists() {
         assertFalse(recordComponentHasAnnotation("beta", R.class, Fizz.class));
      }
   }

   @Nested
   class MethodHasAnnotation {
      @Retention(RetentionPolicy.RUNTIME)
      @Target({ ElementType.METHOD })
      public @interface Buzz {}

      interface I { @Nullable int gamma(); int delta(); }

      @Test
      void isTrueWhenAnnotationExists() {
         assertTrue(methodHasAnnotation("gamma", MethodHasAnnotation.I.class, Nullable.class));
      }

      @Test
      void isFalseWhenAnnotationDoesNotExist() {
         assertFalse(methodHasAnnotation("delta", MethodHasAnnotation.I.class, Nullable.class));
      }

      @Test
      void isFalseWhenWrongAnnotationExists() {
         assertFalse(methodHasAnnotation("gamma", MethodHasAnnotation.I.class, MethodHasAnnotation.Buzz.class));
      }
   }
}