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
@Table(name = "partita")
public class Partita {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "risultato", length = 7)
    private String risultato;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "id_lega", nullable = false)
    private Lega lega;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "id_squadra_casa", nullable = false)
    private Squadra squadraCasa;

    @Column(name = "punti_casa")
    private Float puntiCasa;

    @Column(name = "goal_casa")
    private Integer goalCasa;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "id_squadra_trasf", nullable = false)
    private Squadra squadraTrasf;

    @Column(name = "punti_trasf")
    private Float puntiTrasf;

    @Column(name = "goal_trasf")
    private Integer goalTrasf;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "id_giornata", nullable = false)
    private Giornata giornata;

    @ColumnDefault("'N'")
    @Column(name = "deleted", nullable = false)
    private Character deleted = 'N';

    @OneToMany(mappedBy = "partita")
    private Set<Formazione> formazioni = new LinkedHashSet<>();

}