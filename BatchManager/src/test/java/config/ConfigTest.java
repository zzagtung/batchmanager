package config;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ConfigTest {

  @Test
  public void configLoadingTest() {
    AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(CommonConfiguration.class);
    assertThat(ctx, notNullValue());
  }
}
