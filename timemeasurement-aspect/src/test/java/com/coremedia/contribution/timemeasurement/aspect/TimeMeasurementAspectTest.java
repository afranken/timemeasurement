package com.coremedia.contribution.timemeasurement.aspect;

import com.coremedia.contribution.timemeasurement.TimeMeasurement;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TimeMeasurementAspectTest {

  @Test
  public void testProceed() throws Throwable {
    ProceedingJoinPoint joinPoint = mock(ProceedingJoinPoint.class);
    when(joinPoint.getTarget()).thenReturn("");
    Signature signature = mock(Signature.class);
    when(signature.getName()).thenReturn("testTheBest");
    when(joinPoint.getSignature()).thenReturn(signature);
    System.setProperty("timemeasurement.enabled", "true");

    TimeMeasurementAspect aspect = new TimeMeasurementAspect();

    aspect.profile(joinPoint);

    // verify that proceed was called on the joinPoint
    verify(joinPoint).proceed();

    assertThat("Measurement point was not called.",
            TimeMeasurement.getMeasurementResults().contains("String#testTheBest(..)"));
  }
}
