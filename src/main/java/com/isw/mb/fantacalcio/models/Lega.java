package com.isw.mb.fantacalcio.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "lega")
public class Lega {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "nome", nullable = false, length = 45)
    private String nome;

    @Lob
    @Column(name = "descrizione", columnDefinition = "TEXT")
    private String descrizione;

    @Column(name = "num_squadre", nullable = false)
    private Integer numSquadre;

    @Column(name = "num_crediti", nullable = false)
    private Integer numCrediti;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "ts_creazione", nullable = false)
    private Instant tsCreazione;

    @ColumnDefault("'N'")
    @Column(name = "deleted", nullable = false)
    private Character deleted;

    @OneToMany(mappedBy = "lega")
    private Set<Amministra> amministrazioni = new LinkedHashSet<>();

    @OneToMany(mappedBy = "lega")
    private Set<Partita> partite = new LinkedHashSet<>();

    @OneToMany(mappedBy = "lega")
    private Set<Squadra> squadre = new LinkedHashSet<>();

}