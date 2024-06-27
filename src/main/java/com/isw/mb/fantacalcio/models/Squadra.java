package com.isw.mb.fantacalcio.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "squadra")
public class Squadra {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "nome", nullable = false, length = 45)
    private String nome;

    @ColumnDefault("0")
    @Column(name = "crediti_spesi", nullable = false)
    private Integer creditiSpesi;

    @ColumnDefault("0")
    @Column(name = "num_giocatori", nullable = false)
    private Integer numGiocatori;

    @ColumnDefault("1")
    @Column(name = "pos_class", nullable = false)
    private Integer posClass;

    @ColumnDefault("0")
    @Column(name = "punti_class", nullable = false)
    private Integer puntiClass;

    @ColumnDefault("0")
    @Column(name = "fantapunti", nullable = false)
    private Float fantapunti;

    @ColumnDefault("0")
    @Column(name = "part_giocate", nullable = false)
    private Integer partGiocate;

    @ColumnDefault("0")
    @Column(name = "part_vinte", nullable = false)
    private Integer partVinte;

    @ColumnDefault("0")
    @Column(name = "part_paregg", nullable = false)
    private Integer partParegg;

    @ColumnDefault("0")
    @Column(name = "part_perse", nullable = false)
    private Integer partPerse;

    @ColumnDefault("0")
    @Column(name = "goal_fatti", nullable = false)
    private Integer goalFatti;

    @ColumnDefault("0")
    @Column(name = "goal_subiti", nullable = false)
    private Integer goalSubiti;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "id_allenatore", nullable = false)
    private Allenatore allenatore;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "id_lega", nullable = false)
    private Lega lega;

    @ColumnDefault("'N'")
    @Column(name = "deleted", nullable = false)
    private Character deleted;

    @OneToMany(mappedBy = "squadra")
    private Set<ColoriSquadra> coloriSquadra = new LinkedHashSet<>();

    @OneToMany(mappedBy = "squadra")
    private Set<Formazione> formazioni = new LinkedHashSet<>();

    @OneToMany(mappedBy = "squadra")
    private Set<GiocSquadra> giocatoriSquadra = new LinkedHashSet<>();

    @OneToMany(mappedBy = "squadraCasa")
    private Set<Partita> partiteCasa = new LinkedHashSet<>();

    @OneToMany(mappedBy = "squadraTrasf")
    private Set<Partita> partiteTrasf = new LinkedHashSet<>();

}