package com.coremedia.contribution.timemeasurement.aspect;

import com.coremedia.contribution.timemeasurement.annotation.Measure;
import com.coremedia.contribution.timemeasurement.TimeMeasurement;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class MeasureAspectTest
{
	@Test
	public void testMeasureAspect()
	{
		System.setProperty("timemeasurement.enabled","true");

		consumeTime();

		assertTrue(TimeMeasurement.getMeasurementResults().contains("consumeTime"));
	}

	@Test
	public void testMeasureAspectException()
	{
		System.setProperty("timemeasurement.enabled","true");

		try
		{
			consumeTimeException();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		assertTrue(TimeMeasurement.getMeasurementResults().contains("consumeTimeException"));
	}

	@Measure("consumeTimeException")
	public void consumeTimeException() throws Exception
	{
		Thread.sleep(100);

		throw new Exception();
	}

	@Measure("consumeTime")
	public void consumeTime()
	{
		try
		{
			Thread.sleep(100);
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}
}
