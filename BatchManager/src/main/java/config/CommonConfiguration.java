package config;

import java.sql.SQLException;

import javax.persistence.EntityManagerFactory;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.orm.hibernate4.HibernateExceptionTranslator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configurable
@EnableJpaRepositories(basePackages={"sample.repository"})
@EnableTransactionManagement
public class CommonConfiguration {

  @Bean
  public EntityManagerFactory entityManagerFactory() {
    HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();
    jpaVendorAdapter.setDatabase(Database.MYSQL);
    jpaVendorAdapter.setShowSql(true);
    
    LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
    factory.setJpaVendorAdapter(jpaVendorAdapter);
    factory.setDataSource(dataSource());
    factory.afterPropertiesSet();
    return factory.getObject();
  }
  
  @Bean
  public SimpleDriverDataSource dataSource() {
    SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
    try {
      dataSource.setDriver(new com.mysql.jdbc.Driver());
    } catch (SQLException e) {
      e.printStackTrace();
    }
    dataSource.setUrl("jdbc:mysql://localhost/batch_manager");
//    dataSource.setUsername("appUser");
//    dataSource.setPassword("batchManager!234");
    dataSource.setUsername("root");
    dataSource.setPassword("");
    return dataSource;
  }
  
  @Bean
  public PlatformTransactionManager transactionManager() {
    return new JpaTransactionManager(entityManagerFactory());
  }
  
  @Bean 
  public HibernateExceptionTranslator hibernateExceptionTranslator(){ 
    return new HibernateExceptionTranslator(); 
  }
}
