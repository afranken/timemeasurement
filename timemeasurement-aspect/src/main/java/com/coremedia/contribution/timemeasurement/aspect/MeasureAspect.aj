package com.coremedia.contribution.timemeasurement.aspect;

import com.coremedia.contribution.timemeasurement.TimeMeasurement;
import com.coremedia.contribution.timemeasurement.annotation.Measure;
import etm.core.monitor.EtmPoint;

public aspect MeasureAspect
{
	pointcut hasMeasureAnnotation(Measure measure) : execution(@Measure * * (..)) && @annotation(measure);

	Object around(Measure measure) : hasMeasureAnnotation(measure) {

		EtmPoint etmPoint = null;
		try {
			etmPoint = TimeMeasurement.start(measure.value());
			return proceed(measure);
		} finally {
			TimeMeasurement.stop(etmPoint);
		}
	}
}
