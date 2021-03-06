package com.coremedia.contribution.timemeasurement;

import etm.core.monitor.EtmPoint;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 *
 */
public class TimeMeasurementTest {

  @Test
  public void testWorkingJetmConnector() throws Exception {
    System.setProperty("timemeasurement.enabled", "true");
    TimeMeasurement.reset();

    try(MeasurementResource ignored = TimeMeasurement.start("testWorkingJetmConnector")) {
      Thread.sleep(5);
    } catch (InterruptedException e) {
      e.printStackTrace();
    } finally {
      assertTrue(TimeMeasurement.getMeasurementResults().contains("testWorkingJetmConnector"));
    }
  }

  @Test
  public void testStartWithNullParameter() throws Exception {
    System.setProperty("timemeasurement.enabled", "true");
    TimeMeasurement.reset();

    try(MeasurementResource ignored = TimeMeasurement.start(null)) {
      Thread.sleep(5);
    } catch (InterruptedException e) {
      e.printStackTrace();
    } finally {
      assertTrue(TimeMeasurement.getMeasurementResults().contains("default"));
    }
  }

  @Test
  public void testStopWithNullParameter() throws Exception {
    System.setProperty("timemeasurement.enabled", "true");
    TimeMeasurement.reset();


    TimeMeasurement.stop(null);
  }

  @Test
  public void testIsActive() {
    System.setProperty("timemeasurement.enabled", "true");
    TimeMeasurement.reset();

    assertTrue(TimeMeasurement.getInstance().isActive());
  }

  @Test
  public void testStdOut() {
    System.setProperty("timemeasurement.enabled", "true");
    TimeMeasurement.reset();

    TimeMeasurement.toStdOut();
  }

  @Test
  public void testUseNested() throws Exception {
    System.setProperty("timemeasurement.enabled", "true");
    System.setProperty("timemeasurement.useNested", "true");

    TimeMeasurement.reset();

    EtmPoint etmPoint = null;
    try(MeasurementResource ignored = TimeMeasurement.start("testUseNested")) {
      Thread.sleep(5);
      nestedMethod();
    } catch (InterruptedException e) {
      e.printStackTrace();
    } finally {
      assertTrue(TimeMeasurement.getMeasurementResults().contains("testUseNested"));
      assertTrue(TimeMeasurement.getMeasurementResults().contains("nestedMethod"));
      TimeMeasurement.toLog();
    }
  }

  private void nestedMethod() throws Exception {
    try(MeasurementResource ignored = TimeMeasurement.start("nestedMethod")) {
      Thread.sleep(5);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void testUseMillis() throws Exception {
    System.setProperty("timemeasurement.enabled", "true");
    System.setProperty("timemeasurement.useMillis", "true");
    TimeMeasurement.reset();

    try(MeasurementResource ignored = TimeMeasurement.start("testUseMillis")) {
      //although most systems, Thread.sleep(millis,nanos) does not work (Thread sleeps only for given milliseconds),
      //it's extremly unlikely that this thread would sleep exactly 5 milliseconds.
      //if measured in nanoseconds, it's sleeping ~5100
      Thread.sleep(5, 999);
    } catch (InterruptedException e) {
      e.printStackTrace();
    } finally {
      assertTrue(TimeMeasurement.getMeasurementResults().contains("testUseMillis"));
      TimeMeasurement.toLog();
    }
  }

  @Test
  public void testDummyJetmConnector() throws Exception {
    //unfortunately, the SystemPropery is ignored because TimeMeasurement is initialized in a static block.
    //if one test with "timemeasurement.enabled" set to "true" runs before this test, TimeMeasurement will be
    //initialized with a WorkingJetmConnector and vice versa.
    System.setProperty("timemeasurement.enabled", "false");

    //therefore, I have to manually set it to false, so the DummyJetmConnector is loaded
    TimeMeasurement.getInstance().setActive(false);

    try(MeasurementResource ignored = TimeMeasurement.start("testDummyJetmConnector")) {
      Thread.sleep(5);
    } catch (InterruptedException e) {
      e.printStackTrace();
    } finally {
      assertTrue(!TimeMeasurement.getInstance().isActive());
      assertTrue(TimeMeasurement.getMeasurementResults().contains("disabled"));
    }
  }

}
