package com.isw.mb.fantacalcio.models.combined;

import com.isw.mb.fantacalcio.models.Formazione;
import com.isw.mb.fantacalcio.models.Partita;
import lombok.Getter;

@Getter
public class PartForm {

    private final Partita partita;
    private final Formazione formCasa;
    private final Formazione formTrasf;

    public PartForm(Partita partita, Formazione formCasa, Formazione formTrasf) {
        this.partita = partita;
        this.formCasa = formCasa;
        this.formTrasf = formTrasf;
    }
}
