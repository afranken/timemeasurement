package com.coremedia.contribution.timemeasurement;

import etm.core.configuration.EtmManager;
import etm.core.jmx.EtmMonitorMBean;

import javax.management.*;
import javax.management.openmbean.OpenDataException;
import java.lang.management.ManagementFactory;

/**
 * Helper that registers the MBeans at the JMXProvider.
 */
public final class JmxRegistrationHandler
{
	private static MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
	/**
	 * Private constructor.
	 */
	private JmxRegistrationHandler()
	{
		//do nothing
	}

	/**
	 * Register TimeMeasurementMBean.
	 *
	 * Catches Exception with printStackTrace instead of log output to keep dependencies to a minimum.
	 */
	public static void registerTimeMeasurementMBean()
	{

		try
		{
			if (mBeanServer != null)
			{
				ObjectName timeMeasurementName = new ObjectName("TimeMeasurement:name=TimeMeasurement");
				mBeanServer.registerMBean(com.coremedia.contribution.timemeasurement.TimeMeasurement.getMBean(), timeMeasurementName);
			}
		}
		catch (MalformedObjectNameException e)
		{
			e.printStackTrace(); //NOSONAR
		}
		catch (NotCompliantMBeanException e)
		{
			e.printStackTrace(); //NOSONAR
		}
		catch (InstanceAlreadyExistsException e)
		{
			e.printStackTrace(); //NOSONAR
		}
		catch (MBeanRegistrationException e)
		{
			e.printStackTrace(); //NOSONAR
		}
	}

	/**
	 * Register EtmMonitorMBean.
	 *
	 * Catches Exception with printStackTrace instead of log output to keep dependencies to a minimum.
	 */
	public static void registerEtmMonitorMBean()
	{

		try
		{
			if (mBeanServer != null)
			{
				ObjectName monitorName = new ObjectName("TimeMeasurement:service=PerformanceMonitor");
				mBeanServer.registerMBean(new EtmMonitorMBean(EtmManager.getEtmMonitor(), "TimeMeasurement"), monitorName);
			}
		}
		catch (MalformedObjectNameException e)
		{
			e.printStackTrace(); //NOSONAR
		}
		catch (OpenDataException e)
		{
			e.printStackTrace(); //NOSONAR
		}
		catch (NotCompliantMBeanException e)
		{
			e.printStackTrace(); //NOSONAR
		}
		catch (InstanceAlreadyExistsException e)
		{
			e.printStackTrace(); //NOSONAR
		}
		catch (MBeanRegistrationException e)
		{
			e.printStackTrace(); //NOSONAR
		}
	}
}
