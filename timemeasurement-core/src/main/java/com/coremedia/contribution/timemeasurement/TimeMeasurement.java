package com.coremedia.contribution.timemeasurement;

import com.coremedia.contribution.timemeasurement.connector.JetmConnector;
import com.coremedia.contribution.timemeasurement.connector.NoopJetmConnector;
import com.coremedia.contribution.timemeasurement.connector.WorkingJetmConnector;
import com.coremedia.contribution.timemeasurement.monitoring.JmxRegistrationHandler;
import com.coremedia.contribution.timemeasurement.monitoring.TimeMeasurementMBean;
import etm.core.configuration.BasicEtmConfigurator;
import etm.core.configuration.EtmManager;
import etm.core.monitor.EtmPoint;
import etm.core.timer.DefaultTimer;

/**
 * Helper class that works as an initializer and connector to the JETM library.
 * <p/>
 * Usage:
 * Configure the module "timemeasurement" as a dependency in Maven.
 * The measurement will work if the Java SystemProperty "timemeasurement.enabled" is set to "true"
 * or enabled via the JMX Bean "TimeMeasurement:name=TimeMeasurement".
 * Otherwise, the calls to this class will do nothing.
 * That way, you won't have to remove the calls from your production code if you don't want to and you can also
 * enable time measurement in a production environment via JMX.
 * <p/>
 * This is an example on how to measure time in your code:
 * <p/>
 * EtmPoint etmPoint = null;
 * try {
 * etmPoint = TimeMeasurement.start("nameOfTheMeasurementPoint");
 * //business logic
 * }
 * finally {
 * TimeMeasurement.stop(etmPoint);
 * }
 * <p/>
 * The method "stop" should be called in the finally clause. If an exception occurs in the business logic, the
 * Measurement won't be stopped otherwise.
 * <p/>
 * The class registers the following JMX-MBean: "TimeMeasurement:service=PerformanceMonitor"
 * The method "renderResultsAsText" returns the results as formatted text.
 * Mind that JETM uses blanks to format, so make sure you use a fixed width font when displaying the results.
 * <p/>
 * As a convenience, this class provides two ways of obtaining the measurement data without using JMX:
 * <p/>
 * {@link TimeMeasurement#toStdOut() }
 * and
 * {@link TimeMeasurement#getMeasurementResults()}
 */
public final class TimeMeasurement implements TimeMeasurementMBean {
  private static final TimeMeasurement instance = new TimeMeasurement();
  private static boolean enabled;
  private static boolean useNested;
  private static boolean useMillis;
  private static JetmConnector measurement;

  /**
   * Private constructor
   */
  private TimeMeasurement() {
    enabled = Boolean.getBoolean("timemeasurement.enabled");
    useMillis = Boolean.getBoolean("timemeasurement.useMillis");
    useNested = Boolean.getBoolean("timemeasurement.useNested");
  }

  /**
   * static block initializes the Jetm library and the connector
   */
  static {
    configureEtmManager();
    JmxRegistrationHandler.registerTimeMeasurementMBean();
    chooseJetmConnector();
  }

  /**
   * Start measurement.
   *
   * @param pointName name of the measurement point to create
   * @return measurement point
   */
  public static MeasurementResource start(final String pointName) {
    return new MeasurementResourceImpl(measurement.start(pointName));
  }

  /**
   * Stop measurement.
   *
   * @param point the measurement point to stop
   */
  public static void stop(final MeasurementResource point) throws Exception {
    if (point != null) {
      point.close();
    }
  }

  /**
   * Print formatted measurement information to StandardOut
   */
  public static void toStdOut() {
    measurement.toStdOut();
  }

  /**
   * Print formatted measurement information to Log
   */
  public static void toLog() {
    measurement.toLog();
  }

  /**
   * Get all measurement data as a String
   *
   * @return formatted measurement data
   */
  public static String getMeasurementResults() {
    return measurement.getMeasurementResults();
  }

  // MBean

  @Override
  public boolean isActive() {
    return enabled;
  }

  @Override
  public void setActive(final boolean active) {
    enabled = active;
    chooseJetmConnector();
  }

  @Override
  public boolean isUseNested() {
    return useNested;
  }

  @Override
  public boolean isUseMillis() {
    return useMillis;
  }

  @Override
  public void writeMeasurementResultsToLog() {
    measurement.toLog();
  }


  /**
   * Returns the current instance.
   *
   * @return {@link TimeMeasurementMBean} instance
   */
  public static TimeMeasurementMBean getInstance() {
    return instance;
  }

  public static void reset() {
    EtmManager.reset();
    configureEtmManager();
    chooseJetmConnector();
  }

  //====================================================================================================================

  /**
   * Configures the EtmManager.
   */
  private static void configureEtmManager() {
    DefaultTimer defaultTimer = null;
    if (useMillis) {
      //use DefaultTimer instance to measure in milliseconds instead of nanoseconds
      defaultTimer = new DefaultTimer();
    }

    BasicEtmConfigurator.configure(useNested, defaultTimer);
  }

  /**
   * Returns a WorkingJetmConnector if measurement is enabled, a dummy otherwise
   * Starts or stops the {@link etm.core.configuration.EtmManager#getEtmMonitor()} as necessary.
   */
  private static void chooseJetmConnector() {
    if (enabled) {
      measurement = new WorkingJetmConnector();
      EtmManager.getEtmMonitor().start();
      JmxRegistrationHandler.registerEtmMonitorMBean();
    } else {
      if (EtmManager.getEtmMonitor().isStarted()) {
        EtmManager.getEtmMonitor().stop();
      }
      JmxRegistrationHandler.unregisterEtmMonitorMBean();
      measurement = new NoopJetmConnector();
    }
  }

}
