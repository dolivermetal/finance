package br.com.doliver.database.converter;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

import br.com.doliver.database.entity.OutboxEntity;
import br.com.doliver.domain.Outbox;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface OutboxEntityMapper {
  @Mapping(source = "metadata", target = "metadata")
  @Mapping(target = "integrationStatus", defaultValue = "W")
  OutboxEntity toEntity(Outbox outbox);

  Outbox toOutbox(OutboxEntity entity);

}
