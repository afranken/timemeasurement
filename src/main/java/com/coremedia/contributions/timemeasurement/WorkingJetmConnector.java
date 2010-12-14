package com.coremedia.contributions.timemeasurement;

import etm.core.configuration.EtmManager;
import etm.core.monitor.EtmPoint;
import etm.core.renderer.SimpleTextRenderer;

import java.io.StringWriter;

/**
 * Working connector to the Jetm Library
 */
public class WorkingJetmConnector implements JetmConnector
{
	private static final String DEFAULT_POINTNAME = "default";

	/**
	 * Start einer Messung.
	 *
	 * @param pointName Name des zu erstellenden Messpunktes
	 * @return Messpunkt
	 */
	@Override
	public EtmPoint start(String pointName)
	{
		if (pointName == null || pointName.isEmpty())
		{
			pointName = DEFAULT_POINTNAME;
		}

		return EtmManager.getEtmMonitor().createPoint(pointName);
	}

	@Override
	public void stop(final EtmPoint point)
	{
		if (point != null)
		{
			point.collect();
		}
	}

	@Override
	public void toStdOut()
	{
		EtmManager.getEtmMonitor().render(new SimpleTextRenderer());
	}

	@Override
	public String getMeasurementResults()
	{
		StringWriter writer = new StringWriter();
		EtmManager.getEtmMonitor().render(new SimpleTextRenderer(writer));
		return writer.toString();
	}
}
