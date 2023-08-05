package br.com.doliver.domain;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Definição de um outbox.
 *
 * <p>Outbox é um objeto utilizado para uso do padrão de Transactional outbox, onde uma mensagem é armazenado
 * em banco de dados para ser enviado posteriormente.</p>
 */
public interface Outbox {

  /**
   * ID único de identificação.
   *
   * @return Long - id único de identificação
   */
  Long getId();

  /**
   * Código único de identificação.
   *
   * @return UUID - código único de identificação
   */
  UUID getCode();

  /**
   * Nome do tópico.
   *
   * @return String - Nome do tópico
   */
  String getTopic();

  /**
   * Conteúdo da mensagem.
   *
   * @return String - conteúdo da mensagem
   */
  String getMetadata();

  /**
   * Status para controle de envio da mensagem.
   *
   * @return String - status para controle de envio da mensagem
   */
  String getIntegrationStatus();

  /**
   * Data de cadastro da mensagem.
   *
   * @return LocalDateTime - data de cadastro da pessoa
   */
  LocalDateTime getCreationDate();

  /**
   * Data de atualização da mensagem.
   *
   * @return LocalDateTime - data de atualização da mensagem
   */
  LocalDateTime getUpdateDate();

}
