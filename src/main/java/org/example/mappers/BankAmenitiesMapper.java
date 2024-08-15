package org.example.mappers;

import org.example.dto.ResponseBankAmenities;
import org.example.entity.BankAmenities;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BankAmenitiesMapper {

    List<ResponseBankAmenities> toDtoList(List<BankAmenities> bankAmenities);
}
