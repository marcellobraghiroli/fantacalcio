package com.isw.mb.fantacalcio.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@Embeddable
public class GiocSquadraId implements Serializable {
    private static final long serialVersionUID = -5670825013434330906L;
    @Column(name = "id_giocatore", nullable = false)
    private Integer giocatore;

    @Column(name = "id_squadra", nullable = false)
    private Integer squadra;

    public GiocSquadraId() {}

    public GiocSquadraId(Integer giocatore, Integer squadra) {
        this.giocatore = giocatore;
        this.squadra = squadra;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        GiocSquadraId entity = (GiocSquadraId) o;
        return Objects.equals(this.squadra, entity.squadra) &&
                Objects.equals(this.giocatore, entity.giocatore);
    }

    @Override
    public int hashCode() {
        return Objects.hash(squadra, giocatore);
    }

}