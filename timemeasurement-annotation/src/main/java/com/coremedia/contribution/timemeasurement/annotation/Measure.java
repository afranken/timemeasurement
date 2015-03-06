package com.coremedia.contribution.timemeasurement.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation marks, that the annotated method should
 * be measured.
 * <p/>
 * <p> In order to measure the method, the annotated
 * bytecode must be weaved with the aspect
 * {@link com.coremedia.contribution.timemeasurement.aspect.MeasureAspect}.
 * </p>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = ElementType.METHOD)
public @interface Measure {
  /**
   * Name of the measurement point
   */
  String value() default "";
}
