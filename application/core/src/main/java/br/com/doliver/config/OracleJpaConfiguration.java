package br.com.doliver.config;

import java.util.Objects;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
    basePackages = {"br.com.doliver.database.oracle.repository", "br.com.doliver.database.oracle.entity"},
    entityManagerFactoryRef = "oracleEntityManagerFactory",
    transactionManagerRef = "oracleTransactionManager"
)
public class OracleJpaConfiguration {

  @Bean
  public LocalContainerEntityManagerFactoryBean oracleEntityManagerFactory(
      final @Qualifier("oracleDataSource") DataSource dataSource,
      final EntityManagerFactoryBuilder builder) {
    return builder.dataSource(dataSource)
        .packages("br.com.doliver.database.oracle.entity")
        .build();
  }

  @Bean
  @SuppressFBWarnings(value = "NP_NULL_ON_SOME_PATH_FROM_RETURN_VALUE",
      justification = "Configuração foi feita via bean")
  public PlatformTransactionManager oracleTransactionManager(
      final @Qualifier("oracleEntityManagerFactory")
      LocalContainerEntityManagerFactoryBean oracleEntityManagerFactory) {
    return new JpaTransactionManager(Objects.requireNonNull(oracleEntityManagerFactory.getObject()));
  }
}
