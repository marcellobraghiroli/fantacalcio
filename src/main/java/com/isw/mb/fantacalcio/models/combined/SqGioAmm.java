package com.isw.mb.fantacalcio.models.combined;

import com.isw.mb.fantacalcio.models.Squadra;
import com.isw.mb.fantacalcio.models.Giornata;
import lombok.Getter;

@Getter
public class SqGioAmm {
    private final Squadra squadraCorrente;
    private final Giornata giornata;
    private final boolean isAdmin;
    private final String gradoAdmin;

    public SqGioAmm(Squadra squadraCorrente, Giornata giornata, boolean isAdmin, String gradoAdmin) {
        this.squadraCorrente = squadraCorrente;
        this.giornata = giornata;
        this.isAdmin = isAdmin;
        this.gradoAdmin = gradoAdmin;
    }
}
