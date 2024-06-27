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
@Table(name = "amministra")
public class Amministra {
    @EmbeddedId
    private AmministraId id;

    @MapsId("allenatore")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "id_allenatore", nullable = false)
    private Allenatore allenatore;

    @MapsId("lega")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "id_lega", nullable = false)
    private Lega lega;

    @Column(name = "grado_admin", nullable = false, length = 45)
    private String gradoAdmin;

    @ColumnDefault("'N'")
    @Column(name = "deleted", nullable = false)
    private Character deleted = 'N';

    public Amministra(Allenatore allenatore, Lega lega) {
        this.allenatore = allenatore;
        this.lega = lega;
        this.id = new AmministraId(allenatore.getId(), lega.getId());
    }

    public Amministra() {}

}