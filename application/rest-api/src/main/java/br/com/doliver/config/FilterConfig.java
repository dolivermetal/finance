package br.com.doliver.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {

  @Bean
  public FilterRegistrationBean<OriginFilter> originFilter() {
    FilterRegistrationBean<OriginFilter> filter = new FilterRegistrationBean<>();
    filter.setFilter(new OriginFilter());
    filter.addUrlPatterns("*");
    return filter;
  }

}
