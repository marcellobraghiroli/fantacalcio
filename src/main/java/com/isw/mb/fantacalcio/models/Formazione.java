package com.isw.mb.fantacalcio.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.Instant;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "formazione")
public class Formazione {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "modulo", nullable = false, columnDefinition = "CHAR(5)")
    private String modulo;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "id_squadra", nullable = false)
    private Squadra squadra;

    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "ts_schierata", nullable = false)
    private Instant tsSchierata;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "id_partita", nullable = false)
    private Partita partita;

    @ColumnDefault("'N'")
    @Column(name = "deleted", nullable = false)
    private Character deleted;

    @OneToMany(mappedBy = "formazione")
    private Set<FormGioc> formGiocatori = new LinkedHashSet<>();

}