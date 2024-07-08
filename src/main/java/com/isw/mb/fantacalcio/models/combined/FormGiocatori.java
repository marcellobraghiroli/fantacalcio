package com.isw.mb.fantacalcio.models.combined;

import com.isw.mb.fantacalcio.models.Formazione;
import com.isw.mb.fantacalcio.models.GiocSquadra;
import lombok.Getter;

import java.util.Set;

@Getter
public class FormGiocatori {

    private final Formazione formazione;
    private final Set<GiocSquadra> giocatori;


    public FormGiocatori(Formazione formazione, Set<GiocSquadra> giocatori) {
        this.formazione = formazione;
        this.giocatori = giocatori;
    }
}
