package iloveyouboss.database;

@FunctionalInterface
public interface NewCheckedFunction<T, R, E extends Exception> {
   R apply(T t) throws E;
}