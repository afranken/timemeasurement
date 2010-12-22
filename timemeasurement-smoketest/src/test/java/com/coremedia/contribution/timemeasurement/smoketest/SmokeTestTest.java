package com.coremedia.contribution.timemeasurement.smoketest;

import com.coremedia.contribution.timemeasurement.TimeMeasurement;
import com.coremedia.contribution.timemeasurement.annotation.Measure;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class SmokeTestTest
{
	@Test
	public void testTest()
	{
		System.setProperty("timemeasurement.enabled","true");

		consumeTime();

		TimeMeasurement.toStdOut();
		assertTrue(TimeMeasurement.getMeasurementResults().contains("consumeTime"));
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
