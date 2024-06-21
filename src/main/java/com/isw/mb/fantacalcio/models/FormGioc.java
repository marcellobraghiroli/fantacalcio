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
@Table(name = "form_gioc")
public class FormGioc {
    @EmbeddedId
    private FormGiocId id;

    @MapsId("formazione")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "id_formazione", nullable = false)
    private Formazione formazione;

    @MapsId("giocatore")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "id_giocatore", nullable = false)
    private Giocatore giocatore;

    @Column(name = "titolare", nullable = false)
    private Character titolare;

    @Column(name = "ordine")
    private Integer ordine;

    @ColumnDefault("'N'")
    @Column(name = "deleted", nullable = false)
    private Character deleted;

}