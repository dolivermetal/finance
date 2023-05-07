package br.com.doliver.config;

import javax.sql.DataSource;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OracleDataSourceConfiguration {

  @Bean
  @ConfigurationProperties("spring.datasource.oracle")
  public DataSourceProperties oracleDataSourceProperties() {
    return new DataSourceProperties();
  }

  @Bean
  @ConfigurationProperties("spring.datasource.oracle.hikari")
  public DataSource oracleDataSource() {
    return oracleDataSourceProperties()
        .initializeDataSourceBuilder()
        .build();
  }

}
