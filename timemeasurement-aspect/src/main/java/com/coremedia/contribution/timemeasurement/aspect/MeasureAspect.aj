package com.coremedia.contribution.timemeasurement.aspect;

import com.coremedia.contribution.timemeasurement.TimeMeasurement;
import com.coremedia.contribution.timemeasurement.annotation.Measure;
import etm.core.monitor.EtmPoint;

/**
 * Aspect to be used with aspectj / aspectj-maven-plugin
 */
public aspect MeasureAspect {

  pointcut hasMeasureAnnotation(Measure measure): execution(@Measure * * (..)) && @annotation(measure);

  Object around(Measure measure): hasMeasureAnnotation(measure) {
    // 1 -- get measurement id
    String measureId = measure.value();
    if(measureId.isEmpty()) {
      measureId = thisJoinPoint.getTarget().getClass().getSimpleName() + "#" + thisJoinPoint.getSignature().getName();
    }

    // 2 -- measure and execute method
    EtmPoint etmPoint = null;
    try {
      etmPoint = TimeMeasurement.start(measureId);
      return proceed(measure);
    } finally {
      TimeMeasurement.stop(etmPoint);
    }
  }
}
