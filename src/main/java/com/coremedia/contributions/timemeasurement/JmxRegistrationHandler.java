package com.coremedia.contributions.timemeasurement;

import etm.core.configuration.EtmManager;
import etm.core.jmx.EtmMonitorMBean;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;

/**
 * Helper that registers the MBeans at the JMXProvider.
 */
public final class JmxRegistrationHandler
{
	/**
	 * Private constructor.
	 */
	private JmxRegistrationHandler()
	{
		//do nothing
	}

	/**
	 * Register MBeans.
	 * Catches Exception with printStackTrace instead of log output to keep dependencies to a minimum.
	 */
	public static void register()
	{
		MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
		try
		{
			if (mBeanServer != null)
			{
				ObjectName timeMeasurementName = new ObjectName("TimeMeasurement:name=TimeMeasurement");
				mBeanServer.registerMBean(com.coremedia.contributions.timemeasurement.TimeMeasurement.getMBean(), timeMeasurementName);

				ObjectName monitorName = new ObjectName("TimeMeasurement:service=PerformanceMonitor");
				mBeanServer.registerMBean(new EtmMonitorMBean(EtmManager.getEtmMonitor(), "TimeMeasurement"), monitorName);
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
