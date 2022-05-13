package br.com.doliver.filter;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.MDC;
import org.springframework.web.filter.OncePerRequestFilter;

public class LogMDCRequestFilter extends OncePerRequestFilter {
  @Override
  protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response,
      final FilterChain filterChain)
      throws ServletException, IOException {
    MDC.put("tid", UUID.randomUUID()
        .toString());
    filterChain.doFilter(request, response);
  }
}
