import java.util.SortedMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.codahale.metrics.JmxReporter;
import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.Slf4jReporter;
import com.codahale.metrics.health.HealthCheck;
import com.codahale.metrics.health.HealthCheckRegistry;
import com.himanshu.poc.spring.concepts.config.loader.ContextConfigLoader;
import com.himanshu.poc.spring.concepts.config.loader.SpringHealthCheckConfigurer;
import com.himanshu.poc.spring.concepts.manager.DummyHealthCheckCandidate;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { ContextConfigLoader.class, SpringHealthCheckConfigurer.class })
public class HealthCheckTest {
  
  private final static Logger logger = LoggerFactory.getLogger(HealthCheckTest.class);
  private ScheduledExecutorService scheduledExecutor = Executors.newScheduledThreadPool(1);

  @Autowired
  private MetricRegistry metricRegistry;

  @Autowired
  private HealthCheckRegistry healthCheckRegistry;

  @Autowired
  private DummyHealthCheckCandidate dummyHealthCheckCandidate;
  
  @Before
  public void start() {
    scheduledExecutor.scheduleAtFixedRate(() -> {logger.info(healthCheckRegistry.runHealthChecks().toString());}, 5, 5, TimeUnit.SECONDS);
  }

  private void startReport() {

//    ConsoleReporter reporter = ConsoleReporter.forRegistry(metricRegistry).convertRatesTo(TimeUnit.SECONDS).convertDurationsTo(TimeUnit.MILLISECONDS).build();
//    reporter.start(1, TimeUnit.SECONDS);

    final Slf4jReporter reporter = Slf4jReporter.forRegistry(metricRegistry).outputTo(LoggerFactory.getLogger("com.example.metrics")).convertRatesTo(TimeUnit.SECONDS)
        .convertDurationsTo(TimeUnit.MILLISECONDS).build();
    reporter.start(5, TimeUnit.SECONDS);
    final JmxReporter jmxReporter = JmxReporter.forRegistry(metricRegistry).build();
    jmxReporter.start();

    healthCheckRegistry.register("dummy-health-check-candidate", dummyHealthCheckCandidate);
  }

  private void wait5Seconds() {
    try {
      Thread.sleep(5 * 10000);
    } catch (InterruptedException e) {
    }
  }

  @Test
  public void testHealthCheck() {
    startReport();
    wait5Seconds();
  }
  
  @After
  public void cleanup() {
    scheduledExecutor.shutdownNow();
  }

}
