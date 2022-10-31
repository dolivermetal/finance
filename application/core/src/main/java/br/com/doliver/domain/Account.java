package br.com.doliver.domain;

import java.util.UUID;

/**
 * Definição de uma conta
 */
public interface Account {

  Long getId();

  UUID getUUID();

  String getAlias();

  Person getPerson();

}
