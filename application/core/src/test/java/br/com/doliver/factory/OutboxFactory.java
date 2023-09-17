package br.com.doliver.factory;

import java.time.LocalDateTime;
import java.util.UUID;

import br.com.doliver.domain.Outbox;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class OutboxFactory {

  private static final Long ID = 1L;

  private static final String TOPIC = "topic.name";

  private static final String TOPIC_KEY = "topic.key";

  private static final String TOPIC_MESSAGE = "topic.message";

  private static final String INTEGRATION_STATUS = "W";

  public Outbox getDefault() {
    return getDefaultMock();
  }

  public Outbox getWithEmptyTopic() {
    final OutboxMock mock = getDefaultMock();
    mock.topic = "";
    return mock;
  }

  public Outbox getWithEmptyTopicKey() {
    final OutboxMock mock = getDefaultMock();
    mock.topicKey = "";
    return mock;
  }

  public Outbox getWithEmptyTopicMessage() {
    final OutboxMock mock = getDefaultMock();
    mock.topicMessage = "";
    return mock;
  }

  private OutboxMock getDefaultMock() {
    return OutboxMock.builder()
        .id(ID)
        .code(UUID.randomUUID())
        .topic(TOPIC)
        .topicKey(TOPIC_KEY)
        .topicMessage(TOPIC_MESSAGE)
        .integrationStatus(INTEGRATION_STATUS)
        .creationDate(LocalDateTime.now())
        .updateDate(LocalDateTime.now())
        .build();
  }

  @Getter
  @Builder
  private static final class OutboxMock implements Outbox {

    private Long id;

    private UUID code;

    private String topic;

    private String topicKey;

    private String topicMessage;

    private String integrationStatus;

    private LocalDateTime creationDate;

    private LocalDateTime updateDate;

  }
}
