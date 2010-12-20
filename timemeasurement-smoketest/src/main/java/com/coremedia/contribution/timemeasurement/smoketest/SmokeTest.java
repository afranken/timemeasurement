package com.coremedia.contribution.timemeasurement.smoketest;

import com.coremedia.contribution.timemeasurement.annotation.Measure;
import com.coremedia.contribution.timemeasurement.TimeMeasurement;

public class SmokeTest
{
	public static void main(String[] args)
	{
		System.setProperty("timemeasurement.enabled","true");

		try
		{
			Thread.sleep(50);
			System.out.println("main.Thread.sleep()");
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		testAnnotation();
		TimeMeasurement.toStdOut();
	}

	@Measure("testAnnotation")
	public static void testAnnotation()
	{
		try
		{
			Thread.sleep(50);
			System.out.println("testAnnotation.Thread.sleep()");
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
	}
}
