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
public class GiornCalcId implements Serializable {
    private static final long serialVersionUID = -7294058201975762377L;
    @Column(name = "num_giornata", nullable = false)
    private Integer giornata;

    @Column(name = "id_lega", nullable = false)
    private Integer lega;

    public GiornCalcId() {}

    public GiornCalcId(Integer giornata, Integer lega) {
        this.giornata = giornata;
        this.lega = lega;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        GiornCalcId entity = (GiornCalcId) o;
        return Objects.equals(this.giornata, entity.giornata) &&
                Objects.equals(this.lega, entity.lega);
    }

    @Override
    public int hashCode() {
        return Objects.hash(giornata, lega);
    }

}