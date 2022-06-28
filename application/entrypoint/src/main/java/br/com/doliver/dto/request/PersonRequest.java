package br.com.doliver.dto.request;

import java.util.UUID;

import lombok.Data;

@Data
public class PersonRequest {
  private UUID code;
  private String name;
}
