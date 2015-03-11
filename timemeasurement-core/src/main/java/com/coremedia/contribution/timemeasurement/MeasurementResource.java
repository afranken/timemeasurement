package com.coremedia.contribution.timemeasurement;

import etm.core.monitor.EtmPoint;

/**
 * This class is a wrapper around {@link etm.core.monitor.EtmPoint} that is {@link java.lang.AutoCloseable}
 * to make usage more convenient.
 */
public interface MeasurementResource extends AutoCloseable {

  /**
   * @return the measurement point associated with this resource
   */
  EtmPoint getPoint();
  
}
