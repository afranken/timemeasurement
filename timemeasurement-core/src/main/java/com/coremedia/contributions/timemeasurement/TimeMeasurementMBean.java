package com.coremedia.contributions.timemeasurement;

/**
 * Interface for the TimeMeasurement MBean
 * Methods are available via JMX.
 */
public interface TimeMeasurementMBean
{
	/**
	 * Getter.
	 * true if {@link TimeMeasurement} is active, false if disabled
	 *
	 * @return true or false
	 */
	boolean isActive();

	/**
	 * Setter.
	 * Set parameter active to true to activate {@link TimeMeasurement}, false to disable
	 *
	 * @param active true or false
	 */
	void setActive(boolean active);
}
