package br.com.doliver.kafka;

import lombok.Getter;

@Getter
public enum TopicEnum {

  TRANSACTION("br.com.doliver.finance.transactions");

  private final String name;

  TopicEnum(final String name) {
    this.name = name;
  }
}
