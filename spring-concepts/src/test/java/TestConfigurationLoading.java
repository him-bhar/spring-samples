import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.himanshu.poc.spring.concepts.config.loader.ContextConfigLoader;

/**
 * Run this class with VM Argument set as -Denv=dev
 * @author himanshu
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=ContextConfigLoader.class)
public class TestConfigurationLoading {
  
  @Value("${dev.property}")
  private String devPropValue;
  
  @Value("${global.prop}")
  private String globalPropValue;
  
  
  @Test
  public void test() {
    Assert.assertThat(devPropValue, Matchers.equalTo("Read from system specific"));
    Assert.assertThat(globalPropValue, Matchers.equalTo("Overridden Global Property Read"));
  }
}
