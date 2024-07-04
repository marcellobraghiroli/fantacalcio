package com.isw.mb.fantacalcio.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@Entity
@Table(name = "gioc_squadra")
public class GiocSquadra {
    @EmbeddedId
    private GiocSquadraId id;

    @MapsId("giocatore")
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "id_giocatore", nullable = false)
    private Giocatore giocatore;

    @MapsId("squadra")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "id_squadra", nullable = false)
    private Squadra squadra;

    @Column(name = "prezzo", nullable = false)
    private Integer prezzo;

    @ColumnDefault("'N'")
    @Column(name = "deleted", nullable = false)
    private Character deleted = 'N';

    public GiocSquadra(Giocatore giocatore, Squadra squadra) {
        this.giocatore = giocatore;
        this.squadra = squadra;
        this.id = new GiocSquadraId(giocatore.getId(), squadra.getId());
    }

    public GiocSquadra() {}

}