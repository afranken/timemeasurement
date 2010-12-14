package com.coremedia.contributions.timemeasurement;

import etm.core.configuration.BasicEtmConfigurator;
import etm.core.configuration.EtmManager;
import etm.core.monitor.EtmPoint;

/**
 * Helper class that works as an initializer and connector to the JETM library.
 *
 * Usage:
 * Configure the module "timemeasurement" as a dependency in Maven.
 * The measurement will work if the Java SystemProperty "timemeasurement.enabled" is set to "true".
 * Otherwise, the calls to this class will do nothing.
 * That way, you won't have to remove the calls from your production code if you don't want to.
 *
 * This is an example on how to measure time in your code:
 *
 * EtmPoint etmPoint = null;
 * try {
 * etmPoint = TimeMeasurement.start("nameOfTheMeasurementPoint");
 * //business logic
 * }
 * finally {
 * TimeMeasurement.stop(etmPoint);
 * }
 *
 * The method "stop" should be called in the finally clause. If an exception occurs in the business logic, the
 * Measurement won't be stopped otherwise.
 *
 * The class registers the following JMX-MBean: "TimeMeasurement:service=PerformanceMonitor"
 * The method "renderResultsAsText" returns the results as formatted text.
 * Mind that JETM uses blanks to format, so make sure you use a fixed width font when displaying the results.
 *
 * As a convenience, this class provides two ways of obtaining the measurement data without using JMX:
 *
 * {@link TimeMeasurement#toStdOut() }
 * and
 * {@link TimeMeasurement#getMeasurementResults()}
 */
public final class TimeMeasurement implements TimeMeasurementMBean
{
	static JetmConnector measurement;

	private static TimeMeasurement instance = new TimeMeasurement();
	private boolean active;

	/**
	 * Private constructor
	 */
	private TimeMeasurement()
	{
		active = isMeasurementEnabled();
	}

	/**
	 * static block initializes the JetmConnector
	 */
	static
	{
		chooseJetmConnector(isMeasurementEnabled());
		initializeJetmLibrary(isMeasurementEnabled());
	}

	/**
	 * Start measurement.
	 *
	 * @param pointName name of the measurement point to create
	 * @return measurement point
	 */
	public static EtmPoint start(final String pointName)
	{
		return measurement.start(pointName);
	}

	/**
	 * Stop measurement.
	 *
	 * @param point the measurement point to stop
	 */
	public static void stop(final EtmPoint point)
	{
		measurement.stop(point);
	}

	/**
	 * Print formatted measurement information to StandardOut
	 */
	public static void toStdOut()
	{
		measurement.toStdOut();
	}

	/**
	 * Get all measurement data as a String
	 *
	 * @return formatted measurement data
	 */
	public static String getMeasurementResults()
	{
		return measurement.getMeasurementResults();
	}

	/**
	 * True of the Java SystemProperty "timemeasurement.enabled" is set to "true".
	 * False, if the SystemProperty is set to false or not set at all.
	 *
	 * @return true if enabled
	 */
	public static boolean isMeasurementEnabled()
	{
		return Boolean.getBoolean("timemeasurement.enabled");
	}

	@Override
	public boolean isActive()
	{
		return active;
	}

	@Override
	public void setActive(final boolean active)
	{
		this.active = active;
		chooseJetmConnector(active);
	}

	/**
	 * Returns the current instance.
	 *
	 * @return {@link TimeMeasurementMBean} instance
	 */
	public static TimeMeasurementMBean getMBean()
	{
		return instance;
	}

	/**
	 * Returns a WorkingJetmConnector if measurement is enabled, a dummy otherwise
	 *
	 * @param isMeasurementEnabled if true, measurement will take place
	 */
	private static void chooseJetmConnector(final boolean isMeasurementEnabled)
	{
		if (isMeasurementEnabled)
		{
			measurement = new WorkingJetmConnector();
		} else {
			measurement = new DummyJetmConnector();
		}
	}

	/**
	 * Initialize and configure JETM library, register JMX-Beans.
	 *
	 * @param isMeasurementEnabled if true, JETM will be initialized
	 */
	private static void initializeJetmLibrary(final boolean isMeasurementEnabled)
	{
		if (isMeasurementEnabled)
		{
			BasicEtmConfigurator.configure();
			JmxRegistrationHandler.register();
			EtmManager.getEtmMonitor().start();
		}
	}
}
