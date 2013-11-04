package config;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ConfigTest {

  @Test
  public void configLoadingTest() {
    AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(CommonConfiguration.class,
        BatchConfiguration.class);
    String[] beanNames = ctx.getBeanDefinitionNames();
    for (String beanName : beanNames) {
      System.out.println("====================> " + beanName);
    }
  }
}
