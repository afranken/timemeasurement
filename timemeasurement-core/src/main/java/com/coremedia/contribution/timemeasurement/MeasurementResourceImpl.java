package com.coremedia.contribution.timemeasurement;

import etm.core.monitor.EtmPoint;

/**
 * Simple implementation of {@link com.coremedia.contribution.timemeasurement.MeasurementResource}
 */
public class MeasurementResourceImpl implements MeasurementResource {

  private final EtmPoint point;

  MeasurementResourceImpl(EtmPoint point) {
    this.point = point;
  }

  @Override
  public EtmPoint getPoint() {
    return point;
  }

  @Override
  public void close() {
    if (point != null) {
      point.collect();
    }
  }
}
