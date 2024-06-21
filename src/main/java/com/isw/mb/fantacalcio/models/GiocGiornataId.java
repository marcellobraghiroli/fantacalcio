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
public class GiocGiornataId implements Serializable {
    private static final long serialVersionUID = 6724378787080945188L;
    @Column(name = "id_giocatore", nullable = false)
    private Integer giocatore;

    @Column(name = "num_giornata", nullable = false)
    private Integer giornata;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        GiocGiornataId entity = (GiocGiornataId) o;
        return Objects.equals(this.giornata, entity.giornata) &&
                Objects.equals(this.giocatore, entity.giocatore);
    }

    @Override
    public int hashCode() {
        return Objects.hash(giornata, giocatore);
    }

}