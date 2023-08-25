package br.com.doliver.domain.enums;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class CategoryTest {

  @ParameterizedTest(name = "Dado {0}, deve retornar categoria {1}")
  @DisplayName("Deve retornar a categoria dado um nome")
  @CsvSource(value = {
      "other, OTHER",
      "Other, OTHER",
      "personal care, PERSONAL_CARE",
      "personal-care, PERSONAL_CARE",
      "Personal Care, PERSONAL_CARE"
  })
  void shouldGetCategoryByName(final String name, final Category category) {
    Category categoryFounded = Category.getByName(name);
    Assertions.assertEquals(category, categoryFounded);
  }

  @Test
  @DisplayName("Deve retornar IllegalArgumentException ao consultar uma categoria inválida")
  void shouldThrowIllegalArgumentException() {
    Assertions.assertThrows(IllegalArgumentException.class, () -> Category.getByName("invalid"));
  }
}
