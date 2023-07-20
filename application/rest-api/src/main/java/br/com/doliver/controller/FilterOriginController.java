package br.com.doliver.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(FilterOriginController.BASE_PATH)
public class FilterOriginController {

  public static final String BASE_PATH = "/origin";

  @GetMapping(produces = APPLICATION_JSON_VALUE)
  public ResponseEntity<String> origin(@RequestHeader("origin") final String origin) {
    try {
      log.info("m=test, origin={}", origin);
      return ResponseEntity.ok("{\"origin\" : \"" + origin + "\"}");
    } catch (Exception e) {
      log.error("m=exception, e.message={}", e.getMessage());
      return ResponseEntity.internalServerError()
          .build();
    }
  }

}
