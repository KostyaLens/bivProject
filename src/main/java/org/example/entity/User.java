package org.example.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Generated;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Table
@Data
@Entity(name = "users")
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private LocalDate dateOfCreating = LocalDate.now();

    @Column
    private LocalDate dateOfChange  = LocalDate.now();

    @Column
    private String FIO;

    @Column
    private String email;

    @Column
    private String username;

    @Column
    private String password;
}
