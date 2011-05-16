package com.coremedia.contribution.timemeasurement;

import etm.core.monitor.EtmPoint;

/**
 * Dummy connector, calling this from production code should have only minimal impact.
 */
public class DummyJetmConnector implements JetmConnector {

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
