package iloveyouboss.reflection;

import java.lang.reflect.Field;

public class Reflect {
   // TODO test both of these
   public static Class<?> type(String column, Class<?> dataClass) {
      return field(column, dataClass).getType();
   }

   private static Field field(String column, Class<?> dataClass) {
      try {
         return dataClass.getDeclaredField(column);
      } catch (NoSuchFieldException e) {
         throw new RuntimeException(e);
      }
   }
}
