package br.com.doliver.config;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.web.filter.OncePerRequestFilter;

import lombok.NonNull;

public class OriginFilter extends OncePerRequestFilter {

  private static final Set<String> ALLOWED_ORIGINS = Set.of(
      "teste"
  );

  private static final List<String> IGNORE_PATHS = List.of(
      "/persons",
      "/accounts",
      "/cards/credit",
      "/outbox",
      "/transaction"
  );

  @Override
  protected boolean shouldNotFilter(final @NonNull HttpServletRequest request) {
    return IGNORE_PATHS.stream()
        .anyMatch(path ->
            !Objects.isNull(request.getHeader(HttpHeaders.REFERER))
            && request.getHeader(HttpHeaders.REFERER).contains(path)
            || request.getServletPath().contains(path));
  }

  @Override
  protected void doFilterInternal(
      final @NonNull HttpServletRequest request,
      final @NonNull HttpServletResponse response,
      final @NonNull FilterChain filterChain)
      throws ServletException, IOException {

    String headerOrigin = request.getHeader(HttpHeaders.ORIGIN);

    if (!Objects.isNull(headerOrigin)) {
      if (ALLOWED_ORIGINS.contains(headerOrigin)) {
        filterChain.doFilter(request, response);
      } else {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Requisição não autorizada");
      }
    } else {
      response.sendError(HttpServletResponse.SC_PRECONDITION_FAILED, "Header " + HttpHeaders.ORIGIN + " não existe");
    }
  }
}
