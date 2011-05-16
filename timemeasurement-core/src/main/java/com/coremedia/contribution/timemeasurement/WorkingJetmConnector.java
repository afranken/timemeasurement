package com.coremedia.contribution.timemeasurement;

import etm.core.configuration.EtmManager;
import etm.core.monitor.EtmPoint;
import etm.core.renderer.SimpleTextRenderer;
import etm.core.util.Log;
import etm.core.util.LogAdapter;

import java.io.StringWriter;
import java.text.NumberFormat;

/**
 * Working connector to the Jetm Library
 */
public class WorkingJetmConnector implements JetmConnector {
  private static final String DEFAULT_POINTNAME = "default";
  private static LogAdapter LOG = Log.getLog(WorkingJetmConnector.class);

  @Override
  public EtmPoint start(String pointName) {
    EtmPoint etmPoint;

    if (pointName == null || pointName.isEmpty()) {
      etmPoint = EtmManager.getEtmMonitor().createPoint(DEFAULT_POINTNAME);
    } else {
      etmPoint = EtmManager.getEtmMonitor().createPoint(pointName);
    }

    return etmPoint;
  }

  @Override
  public void stop(final EtmPoint point) {
    if (point != null) {
      point.collect();
    }
  }

  @Override
  public void toStdOut() {
    if (TimeMeasurement.isUseMillisEnabled()) {
      EtmManager.getEtmMonitor().render(new SimpleTextRenderer(NumberFormat.getNumberInstance()));
    } else {
      EtmManager.getEtmMonitor().render(new SimpleTextRenderer());
    }
  }

  @Override
  public String getMeasurementResults() {
    StringWriter writer = new StringWriter();
    if (TimeMeasurement.isUseMillisEnabled()) {
      EtmManager.getEtmMonitor().render(new SimpleTextRenderer(writer, NumberFormat.getNumberInstance()));
    } else {
      EtmManager.getEtmMonitor().render(new SimpleTextRenderer(writer));
    }
    return writer.toString();
  }

  @Override
  public void toLog() {
    LOG.info("\n" + getMeasurementResults());
  }
}
