package org.example.services;

import liquibase.exception.ServiceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.entity.BankAmenities;
import org.example.entity.TypeBankAmenities;
import org.example.repository.BankAmenitiesRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BankAmenitiesService {

    private final BankAmenitiesRepository bankAmenitiesRepository;

    public Page<BankAmenities> bankAmenitiesByTypeAndBankName(String nameBank, int page, int count, String typeAmenities) {
        TypeBankAmenities typeBankAmenities = "Credit".equalsIgnoreCase(typeAmenities) ? TypeBankAmenities.CREDIT : TypeBankAmenities.DEPOSIT;
        System.out.println(typeBankAmenities);
        return bankAmenitiesRepository.findBankAmenitiesByTypeAndBankName(typeBankAmenities, nameBank, PageRequest.of(page, count));
    }

    public BankAmenities getBankAmenitiesByNameBankAndServiceNumber(String nameBank, int serviceNumber, TypeBankAmenities typeBankAmenities) {
        return bankAmenitiesRepository.findBankAmenitiesByTypeAndNumberAndNameBank(typeBankAmenities, serviceNumber, nameBank)
                .orElseThrow(() -> new ServiceNotFoundException("Услуга не найдена"));
    }
}
