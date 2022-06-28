package br.com.doliver.domain.enums;

import lombok.Getter;

public enum Category {
  AUTOMOBILE("Automobile"),
  BAR("Bar"),
  BILLS("Bills"),
  BUY("Buy"),
  FOOD("Food"),
  FUEL("Fuel"),
  HEALTH_CARE("Health Care"),
  HOME("Home"),
  INVESTMENT("Investment"),
  MARKET("Market"),
  OTHERS("Others"),
  PERSONAL_CARE("Personal Care"),
  PET_CARE("Pet Care"),
  TAXES("Taxes"),
  TRANSFER("Transfer"),
  TRANSPORT("Transport"),
  TRAVEL("Travel");

  @Getter
  private final String name;
  Category(final String name) {
    this.name = name;
  }
}

