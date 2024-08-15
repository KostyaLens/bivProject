package org.example.services;

import lombok.RequiredArgsConstructor;
import org.example.entity.BankAmenities;
import org.example.entity.TypeBankAmenities;
import org.example.entity.User;
import org.example.repository.BankAmenitiesRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BankAmenitiesService {
    private final BankAmenitiesRepository bankAmenitiesRepository;

    public Page<BankAmenities> bankAmenitiesByTypeAndBankName(String nameBank, int page, int count, String typeAmenities,
                                                              List<String> sortingDirection){
        TypeBankAmenities typeBankAmenities = "Credit".equalsIgnoreCase(typeAmenities) ? TypeBankAmenities.Credit : TypeBankAmenities.Deposit;
        return bankAmenitiesRepository.findBankAmenitiesByTypeAndBankName(typeBankAmenities, PageRequest.of(page, count));
    }

}
