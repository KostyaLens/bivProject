package org.example.entity;

import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Index;
import lombok.*;

import java.util.Set;

@Entity(name = "bank")
@Table(indexes = @Index(columnList = "name"))
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Bank {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String name;

    @Column
    private long budget;

    @OneToMany(mappedBy = "bank", cascade = CascadeType.ALL)
    private Set<Account> account;

    @OneToMany(mappedBy = "bank", cascade = CascadeType.ALL)
    private Set<BankAmenities> bankAmenities;
}
