package iloveyouboss.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;

public class ReflectUtils {
   public static Class<?> type(String column, Class<?> dataClass) {
      return field(column, dataClass).getType();
   }

   public static Field field(String column, Class<?> dataClass) {
      try {
         return dataClass.getDeclaredField(column);
      } catch (NoSuchFieldException e) {
         throw new RuntimeException(e);
      }
   }

   public static Method method(String column, Class<?> dataClass) {
      try {
         return dataClass.getDeclaredMethod(column);
      } catch (NoSuchMethodException e) {
         throw new RuntimeException(e);
      }
   }

   public static boolean recordComponentHasAnnotation(
         String column, Class<?> recordClass, Class<? extends Annotation> annotationClass) {
      var component = Arrays.stream(recordClass.getRecordComponents())
         .filter(c -> c.getName().equals(column))
         .findFirst()
         .orElseThrow();
      return component.isAnnotationPresent(annotationClass);
   }

   public static Class<?> accessorType(String column, Class<?> dataClass) {
      return method(column, dataClass).getReturnType();
   }

   public static boolean methodHasAnnotation(String column, Class<?> dataClass, Class<? extends Annotation> annotationClass) {
      var method = method(column, dataClass);
      return method.isAnnotationPresent(annotationClass);
   }
}
