package com.coremedia.contribution.timemeasurement.connector;

import etm.core.monitor.EtmPoint;

/**
 * Non-operational connector, calling this from production code has only minimal impact.
 */
public class NoopJetmConnector implements JetmConnector {

  @Override
  public EtmPoint start(final String pointName) {
    //do nothing
    return null;
  }

  @Override
  public void stop(final EtmPoint point) {
    //do nothing
  }

  @Override
  public void toStdOut() {
    //do nothing
  }

  @Override
  public String getMeasurementResults() {
    return "TimeMeasurement is disabled. Please do not call this method.";
  }

  @Override
  public void toLog() {
    //do nothing
  }
}
