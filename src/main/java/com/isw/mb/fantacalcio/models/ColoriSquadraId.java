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
public class ColoriSquadraId implements Serializable {
    private static final long serialVersionUID = 3896560702650293537L;
    @Column(name = "id_squadra", nullable = false)
    private Integer squadra;

    @Column(name = "colore", nullable = false, length = 45)
    private String colore;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ColoriSquadraId entity = (ColoriSquadraId) o;
        return Objects.equals(this.squadra, entity.squadra) &&
                Objects.equals(this.colore, entity.colore);
    }

    @Override
    public int hashCode() {
        return Objects.hash(squadra, colore);
    }

}