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
@Table(name = "colori_squadra")
public class ColoriSquadra {
    @EmbeddedId
    private ColoriSquadraId id;

    @MapsId("squadra")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "id_squadra", nullable = false)
    private Squadra squadra;

    @ColumnDefault("'N'")
    @Column(name = "deleted", nullable = false)
    private Character deleted;

}