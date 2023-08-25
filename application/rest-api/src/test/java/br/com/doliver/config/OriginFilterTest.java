package br.com.doliver.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpHeaders;

import static org.junit.jupiter.api.Assertions.assertAll;

public class OriginFilterTest {

  @Mock
  private HttpServletRequest request;

  @Mock
  private HttpServletResponse response;

  @Mock
  private FilterChain filterChain;

  @BeforeEach
  void setup() {
    this.request = Mockito.spy(HttpServletRequest.class);
    this.response = Mockito.spy(HttpServletResponse.class);
    this.filterChain = Mockito.spy(FilterChain.class);
  }

  @ParameterizedTest(name = "Dado path {0}, deve retornar boolean {1} se deve ou não filtrar")
  @DisplayName("Deve retornar boolean se aplica filtro para cada path")
  @CsvSource(value = {
      "/persons, true",
      "/accounts, true",
      "/others, false"
  })
  void shouldNotFilter(final String path, final boolean applyFilter) {
    Mockito.when(request.getHeader(HttpHeaders.REFERER))
        .thenReturn(path);

    Mockito.when(request.getServletPath())
        .thenReturn(path);

    final OriginFilter originFilter = new OriginFilter();
    boolean shouldNotFilter = originFilter.shouldNotFilter(request);

    Assertions.assertEquals(applyFilter, shouldNotFilter);
  }

  @Test
  @DisplayName("Deve aplicar filtro de origem")
  void shouldDoFilterInternal() throws ServletException, IOException {
    Mockito.when(request.getHeader(HttpHeaders.ORIGIN))
        .thenReturn("teste");

    final OriginFilter originFilter = new OriginFilter();
    originFilter.doFilterInternal(request, response, filterChain);

    assertAll(
        () -> Mockito.verify(filterChain, Mockito.times(1))
            .doFilter(request, response)
    );
  }

  @Test
  @DisplayName("Deve retornar Unauthorized")
  void shouldThrowUnauthorized() throws ServletException, IOException {
    Mockito.when(request.getHeader(HttpHeaders.ORIGIN))
        .thenReturn("erro");

    final OriginFilter originFilter = new OriginFilter();
    originFilter.doFilterInternal(request, response, filterChain);

    assertAll(
        () -> Mockito.verify(filterChain, Mockito.times(0))
            .doFilter(request, response)
    );
  }

  @Test
  @DisplayName("Deve retornar Pre-condition Failed")
  void shouldThrowPreConditionFailed() throws ServletException, IOException {
    Mockito.when(request.getHeader(HttpHeaders.ORIGIN))
        .thenReturn(null);

    final OriginFilter originFilter = new OriginFilter();
    originFilter.doFilterInternal(request, response, filterChain);

    assertAll(
        () -> Mockito.verify(filterChain, Mockito.times(0))
            .doFilter(request, response)
    );
  }
}
