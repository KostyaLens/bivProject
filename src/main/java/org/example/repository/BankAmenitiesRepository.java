package org.example.repository;

import org.example.entity.Bank;
import org.example.entity.BankAmenities;
import org.example.entity.TypeBankAmenities;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BankAmenitiesRepository extends JpaRepository<BankAmenities, Long> {

    @Query("SELECT ba FROM bank_amenities ba JOIN FETCH ba.bank b WHERE ba.type = :type AND b.name = :bankName")
    Page<BankAmenities> findBankAmenitiesByTypeAndBankName(@Param("type") TypeBankAmenities type, @Param("bankName") String bankName, Pageable pageable);

    Page<BankAmenities> findBankAmenitiesByType(TypeBankAmenities typeBankAmenities, Pageable pageable);

    @Query("SELECT ba FROM bank_amenities ba JOIN FETCH ba.bank b WHERE ba.type = :type AND ba.number = :number AND b.name = :bankName")
    Optional<BankAmenities> findBankAmenitiesByTypeAndNumberAndNameBank(@Param("type") TypeBankAmenities type, @Param("number") int number, @Param("bankName") String nameBank);
}
