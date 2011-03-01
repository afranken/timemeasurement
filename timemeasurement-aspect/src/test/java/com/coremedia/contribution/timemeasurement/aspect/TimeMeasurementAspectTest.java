package com.coremedia.contribution.timemeasurement.aspect;

import com.coremedia.contribution.timemeasurement.TimeMeasurement;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.junit.Test;
import org.mockito.Mockito;

import static org.junit.Assert.assertTrue;

public class TimeMeasurementAspectTest {

  @Test
  public void testProceed() throws Throwable {
    ProceedingJoinPoint joinPoint = Mockito.mock(ProceedingJoinPoint.class);
    Mockito.when(joinPoint.getTarget()).thenReturn("");
    Signature signature = Mockito.mock(Signature.class);
    Mockito.when(signature.getName()).thenReturn("testTheBest");
    Mockito.when(joinPoint.getSignature()).thenReturn(signature);
    System.setProperty("timemeasurement.enabled", "true");

    TimeMeasurementAspect aspect = new TimeMeasurementAspect();

    aspect.profile(joinPoint);

    // verify that proceed was called on the joinPoint
    Mockito.verify(joinPoint).proceed();

    assertTrue(TimeMeasurement.getMeasurementResults().contains("java.lang.String.testTheBest(..)"));
  }
}
