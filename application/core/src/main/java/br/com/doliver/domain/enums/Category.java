package br.com.doliver.domain.enums;

/**
 * Categorias para classificação das transações.
 */
public enum Category {

  /**
   * 0. Automóvel
   */
  AUTOMOBILE("Automobile"),

  /**
   * 1. Bar
   */
  BAR("Bar"),

  /**
   * 2. Conta
   */
  BILL("Bill"),

  /**
   * 3. Compras
   */
  BUY("Buy"),

  /**
   * 4. Taxa
   */
  FEE("Fee"),

  /**
   * 5. Alimentação
   */
  FOOD("Food"),

  /**
   * 6. Combustível
   */
  FUEL("Fuel"),

  /**
   * 7. Saúde
   */
  HEALTH_CARE("Health Care"),

  /**
   * 8. Casa
   */
  HOME("Home"),

  /**
   * 9. Investimento
   */
  INVESTMENT("Investment"),

  /**
   * 10. Mercado
   */
  MARKET("Market"),

  /**
   * 11. Outros
   */
  OTHER("Other"),

  /**
   * 12. Cuidado pessoal
   */
  PERSONAL_CARE("Personal Care"),

  /**
   * 13. Cuidado com animal de estimação
   */
  PET_CARE("Pet Care"),

  /**
   * 14. Transferência
   */
  TRANSFER("Transfer"),

  /**
   * 15. Transporte
   */
  TRANSPORT("Transport"),

  /**
   * 16. Viagem
   */
  TRAVEL("Travel");

  Category(final String name) {
  }

  public static Category getByName(final String name) {
    return Category.valueOf(name.toUpperCase().replaceAll(" |-", "_"));
  }

}

