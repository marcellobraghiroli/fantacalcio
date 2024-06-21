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
@Table(name = "giornata")
public class Giornata {
    @Id
    @Column(name = "numero", nullable = false)
    private Integer id;

    @Column(name = "ts_inizio", nullable = false)
    private Instant tsInizio;

    @ColumnDefault("'N'")
    @Column(name = "deleted", nullable = false)
    private Character deleted;

    @OneToMany(mappedBy = "giornata")
    private Set<GiocGiornata> giocatoriGiorn = new LinkedHashSet<>();

    @OneToMany(mappedBy = "giornata")
    private Set<Partita> partite = new LinkedHashSet<>();

}