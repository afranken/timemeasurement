package com.coremedia.contribution.timemeasurement;

import etm.core.configuration.EtmManager;
import etm.core.jmx.EtmMonitorMBean;
import etm.core.util.Log;
import etm.core.util.LogAdapter;

import javax.management.*;
import javax.management.openmbean.OpenDataException;
import java.lang.management.ManagementFactory;
import java.util.ArrayList;

/**
 * Helper that registers and unregisters the MBeans at the JMXProvider.
 */
public final class JmxRegistrationHandler {
  private static MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
  private static ObjectName timeMeasurementName;
  private static ObjectName monitorName;
  private static LogAdapter LOG = Log.getLog(JmxRegistrationHandler.class);

  /**
   * Private constructor.
   */
  private JmxRegistrationHandler() {
    //do nothing
  }

  /**
   * Static block initializes the mBean names.
   */
  static {
    try {
      timeMeasurementName = new ObjectName("TimeMeasurement:service=TimeMeasurement");
      monitorName = new ObjectName("TimeMeasurement:service=PerformanceMonitor");
    } catch (MalformedObjectNameException e) {
      LOG.error("Could not create MBean ObjectNames. ", e);
    }
  }

  /**
   * Register TimeMeasurementMBean.
   */
  public static void registerTimeMeasurementMBean() {

    try {
      ArrayList<MBeanServer> mBeanServers = MBeanServerFactory.findMBeanServer(null);

      for (MBeanServer mBeanServer : mBeanServers) {
        mBeanServer.registerMBean(com.coremedia.contribution.timemeasurement.TimeMeasurement.getMBean(), timeMeasurementName);
      }

    } catch (NotCompliantMBeanException e) {
      LOG.warn("TimeMeasurementMBean is not compliant with the mBeanServer you are using. ", e);
    } catch (InstanceAlreadyExistsException e) {
      LOG.debug("TimeMeasurementMBean already exists");
    } catch (MBeanRegistrationException e) {
      LOG.warn("TimeMeasurementMBean could not be registered at the MBeanServer. ", e);
    }
  }

  /**
   * Register EtmMonitorMBean.
   * <p/>
   * Catches Exception with printStackTrace instead of log output to keep dependencies to a minimum.
   */
  public static void registerEtmMonitorMBean() {

    try {
      ArrayList<MBeanServer> mBeanServers = MBeanServerFactory.findMBeanServer(null);

      for (MBeanServer mBeanServer : mBeanServers) {
        mBeanServer.registerMBean(new EtmMonitorMBean(EtmManager.getEtmMonitor(), "TimeMeasurement"), monitorName);
      }
    } catch (OpenDataException e) {
      LOG.warn("There was a problem opening the EtmMonitorMBean.", e);
    } catch (NotCompliantMBeanException e) {
      LOG.warn("EtmMonitorMBean is not compliant with the mBeanServer you are using. ", e);
    } catch (InstanceAlreadyExistsException e) {
      LOG.debug("EtmMonitorMBean already exists");
    } catch (MBeanRegistrationException e) {
      LOG.warn("EtmMonitorMBean could not be registered at the MBeanServer. ", e);
    }
  }

  /**
   * Unregister EtmMonitorMBean.
   */
  public static void unregisterEtmMonitorMBean() {

    try {
      if (mBeanServer != null) {
        mBeanServer.unregisterMBean(monitorName);
      }
    } catch (MBeanRegistrationException e) {
      LOG.warn("EtmMonitorMBean could not be unregistered. ", e);
    } catch (InstanceNotFoundException e) {
      LOG.debug("EtmMonitorMBean instance could not be found.");
    }
  }
}
