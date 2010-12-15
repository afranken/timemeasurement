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
			Thread.sleep(5);
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
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
			Thread.sleep(5);
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

	@Test
	public void testIsActive()
	{
		System.setProperty("timemeasurement.enabled","true");

		assertTrue(TimeMeasurement.getMBean().isActive());
	}

	@Test
	public void testStdOut()
	{
		System.setProperty("timemeasurement.enabled","true");

		TimeMeasurement.toStdOut();
	}

	@Test
	public void testDummyJetmConnector()
	{
		//unfortunately, the SystemPropery is ignored because TimeMeasurement is initialized in a static block.
		//if one test with "timemeasurement.enabled" set to "true" runs before this test, TimeMeasurement will be
		//initialized with a WorkingJetmConnector and vice versa.
		System.setProperty("timemeasurement.enabled","false");

		//therefore, I have to manually set it to false, so the DummyJetmConnector is loaded
		TimeMeasurement.getMBean().setActive(false);

		EtmPoint etmPoint = null;
		try
		{
			etmPoint = TimeMeasurement.start("testDummyJetmConnector");
			Thread.sleep(5);
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		finally
		{
			TimeMeasurement.stop(etmPoint);
			TimeMeasurement.toStdOut();
			assertTrue(TimeMeasurement.getMeasurementResults().contains("disabled"));
		}
	}

}
