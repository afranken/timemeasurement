package com.coremedia.contribution.timemeasurement;

import etm.core.monitor.EtmPoint;

/**
 * Connector to the Jetm Library.
 */
public interface JetmConnector {
  /**
   * Start measurement.
   *
   * @param pointName name of the measurement point to create
   * @return measurement point
   */
  EtmPoint start(String pointName);

  /**
   * Stop measurement.
   *
   * @param point the measurement point to stop
   */
  void stop(EtmPoint point);

  /**
   * Print formatted measurement information to StandardOut
   */
  void toStdOut();

  /**
   * Get all measurement data as a String
   *
   * @return formatted measurement data
   */
  String getMeasurementResults();

  /**
   * Try to write formatted measurement information to Log4j or Java Log.
   * Write to System.out.println as a fallback.
   */
  void toLog();

}
