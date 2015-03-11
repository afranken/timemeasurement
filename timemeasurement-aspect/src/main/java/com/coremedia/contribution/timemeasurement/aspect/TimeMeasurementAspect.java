package com.coremedia.contribution.timemeasurement.aspect;

import com.coremedia.contribution.timemeasurement.MeasurementResource;
import com.coremedia.contribution.timemeasurement.TimeMeasurement;
import org.aspectj.lang.ProceedingJoinPoint;

/**
 * An aspect for Spring AOP or AspectJ that adds timemeasurement feature using the
 * {@link com.coremedia.contribution.timemeasurement.TimeMeasurement} implementation for "around" advices.
 * <p/>
 * This implementation uses the {@link org.aspectj.lang.ProceedingJoinPoint#getTarget()} fully-qualified class name
 * and the method name as point name for {@link com.coremedia.contribution.timemeasurement.TimeMeasurement#start(String)}.
 * E.g. when invoked on {@link String#toString()} it will use
 * <pre>java.lang.String.toString(..)</pre> as point name.
 * <p/>
 * Example configuration for Spring AOP that measures the duration of all toString() calls (warning: might be a lot):
 * <p/>
 * <pre>
 *  &lt;bean id="timemeasurementAspectBean" class="com.coremedia.contribution.timemeasurement.aspect.TimeMeasurementAspect"/>
 *  &lt;aop:config>
 *     &lt;aop:aspect id="timemeasurementAspect" ref="timemeasurementAspectBean">
 *       &lt;aop:pointcut id="myPointcut"
 *                     expression="execution(String *.toString()"/>
 *       &lt;aop:around pointcut-ref="myPointcut" method="profile"/>
 *     &lt;/aop:aspect>
 *  &lt;/aop:config>
 * </pre>
 */
public class TimeMeasurementAspect {

  public Object profile(ProceedingJoinPoint call) throws Throwable {

    String measureId = String.format("%1$s#%2$s(..)",
            call.getTarget().getClass().getSimpleName(),
            call.getSignature().getName());

    try (MeasurementResource ignored = TimeMeasurement.start(measureId)) {
      return call.proceed();
    }
  }
}
