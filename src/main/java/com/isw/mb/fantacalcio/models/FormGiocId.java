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
public class FormGiocId implements Serializable {
    private static final long serialVersionUID = -9028192291460193804L;
    @Column(name = "id_formazione", nullable = false)
    private Integer formazione;

    @Column(name = "id_giocatore", nullable = false)
    private Integer giocatore;

    public FormGiocId() {}

    public FormGiocId(Integer formazione, Integer giocatore) {
        this.formazione = formazione;
        this.giocatore = giocatore;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        FormGiocId entity = (FormGiocId) o;
        return Objects.equals(this.formazione, entity.formazione) &&
                Objects.equals(this.giocatore, entity.giocatore);
    }

    @Override
    public int hashCode() {
        return Objects.hash(formazione, giocatore);
    }

}