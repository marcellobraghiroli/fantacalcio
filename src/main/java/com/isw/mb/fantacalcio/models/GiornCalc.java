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
@Table(name = "giorn_calc")
public class GiornCalc {
    @EmbeddedId
    private GiornCalcId id;

    @MapsId("giornata")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "num_giornata", nullable = false)
    private Giornata giornata;

    @MapsId("lega")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "id_lega", nullable = false)
    private Lega lega;

    @ColumnDefault("'N'")
    @Column(name = "deleted", nullable = false)
    private Character deleted = 'N';

    public GiornCalc(Giornata giornata, Lega lega) {
        this.giornata = giornata;
        this.lega = lega;
        this.id = new GiornCalcId(giornata.getNumero(), lega.getId());
    }

    public GiornCalc() {}

}