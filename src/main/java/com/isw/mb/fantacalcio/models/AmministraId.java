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
public class AmministraId implements Serializable {
    private static final long serialVersionUID = 7416056320592422457L;
    @Column(name = "id_allenatore", nullable = false)
    private Integer allenatore;

    @Column(name = "id_lega", nullable = false)
    private Integer lega;

    public AmministraId() {}

    public AmministraId(Integer allenatore, Integer lega) {
        this.allenatore = allenatore;
        this.lega = lega;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        AmministraId entity = (AmministraId) o;
        return Objects.equals(this.lega, entity.lega) &&
                Objects.equals(this.allenatore, entity.allenatore);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lega, allenatore);
    }

}