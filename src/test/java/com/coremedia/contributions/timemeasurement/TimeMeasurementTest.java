package com.coremedia.contributions.timemeasurement;

import etm.core.monitor.EtmPoint;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * 
 */
public class TimeMeasurementTest
{

	@Test
	public void testWorkingJetmConnector()
	{
		System.setProperty("timemeasurement.enabled","true");

		EtmPoint etmPoint = null;
		try
		{
			etmPoint = TimeMeasurement.start("testWorkingJetmConnector");
		}
		finally
		{
			TimeMeasurement.stop(etmPoint);
			assertTrue(TimeMeasurement.getMeasurementResults().contains("testWorkingJetmConnector"));
		}
	}

	@Test
	public void testStartWithNullParameter()
	{
		System.setProperty("timemeasurement.enabled","true");

		EtmPoint etmPoint = null;
		try
		{
			etmPoint = TimeMeasurement.start(null);
			Thread.sleep(500);
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		finally
		{
			TimeMeasurement.stop(etmPoint);
			assertTrue(TimeMeasurement.getMeasurementResults().contains("default"));
		}
	}

	@Test
	public void testStopWithNullParameter()
	{
		System.setProperty("timemeasurement.enabled","true");

		TimeMeasurement.stop(null);
	}

}
