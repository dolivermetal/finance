package br.com.doliver.database.converter;

import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import br.com.doliver.database.entity.TransactionEntity;
import br.com.doliver.domain.Transaction;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
    unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TransactionEntityMapper {

  TransactionEntity toEntity(Transaction transaction);

  Transaction toDomain(TransactionEntity transaction);

}
