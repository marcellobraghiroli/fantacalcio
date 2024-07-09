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
@Table(name = "gioc_giornata")
public class GiocGiornata {
    @EmbeddedId
    private GiocGiornataId id;

    @MapsId("giocatore")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "id_giocatore", nullable = false)
    private Giocatore giocatore;

    @MapsId("giornata")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "num_giornata", nullable = false)
    private Giornata giornata;

    @Column(name = "voto")
    private Float voto;

    @Column(name = "fantavoto")
    private Float fantavoto;

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

    @ColumnDefault("'N'")
    @Column(name = "ammonito", nullable = false)
    private Character ammonito;

    @ColumnDefault("'N'")
    @Column(name = "espulso", nullable = false)
    private Character espulso;

    @ColumnDefault("'N'")
    @Column(name = "deleted", nullable = false)
    private Character deleted = 'N';

    public GiocGiornata(Giocatore giocatore, Giornata giornata) {
        this.giocatore = giocatore;
        this.giornata = giornata;
        this.id = new GiocGiornataId(giocatore.getId(), giornata.getNumero());
    }

    public GiocGiornata() {}

}