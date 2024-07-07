package com.isw.mb.fantacalcio.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "giocatore")
public class Giocatore {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "nome", nullable = false, length = 90)
    private String nome;

    @Column(name = "squadra_reale", nullable = false, length = 45)
    private String squadraReale;

    @Column(name = "ruolo", nullable = false, columnDefinition = "CHAR(3)")
    private String ruolo;

    @ColumnDefault("0")
    @Column(name = "media", nullable = false)
    private Float media;

    @ColumnDefault("0")
    @Column(name = "fantamedia", nullable = false)
    private Float fantamedia;

    @ColumnDefault("0")
    @Column(name = "presenze", nullable = false)
    private Integer presenze;

    @ColumnDefault("0")
    @Column(name = "goal_fatti", nullable = false)
    private Integer goalFatti;

    @ColumnDefault("0")
    @Column(name = "goal_subiti", nullable = false)
    private Integer goalSubiti;

    @ColumnDefault("0")
    @Column(name = "rig_sbagliati", nullable = false)
    private Integer rigSbagliati;

    @ColumnDefault("0")
    @Column(name = "rig_parati", nullable = false)
    private Integer rigParati;

    @ColumnDefault("0")
    @Column(name = "assist", nullable = false)
    private Integer assist;

    @ColumnDefault("0")
    @Column(name = "autogoal", nullable = false)
    private Integer autogoal;

    @ColumnDefault("0")
    @Column(name = "ammonizioni", nullable = false)
    private Integer ammonizioni;

    @ColumnDefault("0")
    @Column(name = "espulsioni", nullable = false)
    private Integer espulsioni;

    @ColumnDefault("'N'")
    @Column(name = "deleted", nullable = false)
    private Character deleted;

    @OneToMany(mappedBy = "giocatore")
    private Set<FormGioc> formazioniGioc = new LinkedHashSet<>();

    @OneToMany(mappedBy = "giocatore")
    private Set<GiocGiornata> giocGiornate = new LinkedHashSet<>();

    @OneToMany(mappedBy = "giocatore")
    private Set<GiocSquadra> giocSquadre = new LinkedHashSet<>();

    @Transient
    private GiocGiornata giocGiornata;

}