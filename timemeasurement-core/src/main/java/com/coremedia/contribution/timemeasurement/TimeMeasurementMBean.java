package com.coremedia.contribution.timemeasurement;

/**
 * Interface for the TimeMeasurement MBean
 * Methods are available via JMX.
 */
public interface TimeMeasurementMBean {
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

  /**
   * Getter.
   * true if {@link TimeMeasurement} should return nested results, false if disabled
   *
   * @return true or false
   */
  public boolean isUseNested();

  /**
   * Getter.
   * true if {@link TimeMeasurement} should use milliseconds during measurement, false if nanoseconds should be used.
   *
   * @return true or false
   */
  public boolean isUseMillis();

  /**
   * Print formatted measurement information to StandardOut
   */
  public void writeMeasurementResultsToLog();
}
