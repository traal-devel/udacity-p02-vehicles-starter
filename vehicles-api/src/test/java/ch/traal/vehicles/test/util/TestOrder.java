package ch.traal.vehicles.test.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)


@Target({ ElementType.METHOD})
public @interface TestOrder {
  public int order();
}