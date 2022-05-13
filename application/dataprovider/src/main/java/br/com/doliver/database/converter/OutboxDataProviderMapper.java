package br.com.doliver.database.converter;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.NullValuePropertyMappingStrategy;

import br.com.doliver.database.entity.OutboxEntity;
import br.com.doliver.domain.Outbox;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface OutboxDataProviderMapper {
  @Mapping(source = "metadata", target = "metadata")
  @Mapping(source = "metadata", target = "metadataText", qualifiedByName = "toBase64String")
  @Mapping(source = "metadata", target = "metadataByte", qualifiedByName = "toBase64")
  @Mapping(target = "integrationStatus", defaultValue = "W")
  OutboxEntity toEntity(Outbox outbox);

  @Mapping(source = "metadataByte", target = "metadata", qualifiedByName = "fromBase64")
  Outbox toOutbox(OutboxEntity entity);

  @Named(value = "toBase64String")
  default String toBase64String(String metadata) {
    return Base64.getEncoder()
        .encodeToString(metadata.getBytes(StandardCharsets.UTF_8));
  }

  @Named(value = "toBase64")
  default byte[] toBase64(String metadata) {
    return Base64.getEncoder()
        .encode(metadata.getBytes(StandardCharsets.UTF_8));
  }

  @Named(value = "fromBase64String")
  default String fromBase64String(String metadata) {
    return new String(Base64.getDecoder()
        .decode(metadata.getBytes(StandardCharsets.UTF_8)));
  }

  @Named(value = "fromBase64")
  default String fromBase64(byte[] metadata) {
    return new String(Base64.getDecoder()
        .decode(metadata));
  }

}
