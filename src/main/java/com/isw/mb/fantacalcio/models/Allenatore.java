package com.isw.mb.fantacalcio.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "allenatore")
public class Allenatore {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "nome", nullable = false, length = 45)
    private String nome;

    @Column(name = "cognome", nullable = false, length = 45)
    private String cognome;

    @Column(name = "username", nullable = false, length = 45)
    private String username;

    @Column(name = "password", nullable = false, length = 45)
    private String password;

    @Column(name = "data_nascita", nullable = false)
    private LocalDate dataNascita;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "ts_creazione", nullable = false)
    private LocalDateTime tsCreazione = LocalDateTime.now();

    @Column(name = "telefono", length = 45)
    private String telefono;

    @Column(name = "email", nullable = false, length = 45)
    private String email;

    @ColumnDefault("'N'")
    @Column(name = "deleted", nullable = false)
    private Character deleted = 'N';

    @OneToMany(mappedBy = "allenatore")
    private Set<Amministra> amministrazioni = new LinkedHashSet<>();

    @OneToMany(mappedBy = "allenatore")
    private Set<Squadra> squadre = new LinkedHashSet<>();

}