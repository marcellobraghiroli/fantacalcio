package com.isw.mb.fantacalcio.models.combined;

import com.isw.mb.fantacalcio.models.Squadra;
import com.isw.mb.fantacalcio.models.Giornata;
import lombok.Getter;

@Getter
public class SqGioAmm {
    private final Squadra squadraCorrente;
    private final Giornata giornata;
    private final boolean isAdmin;

    public SqGioAmm(Squadra squadraCorrente, Giornata giornata, boolean isAdmin) {
        this.squadraCorrente = squadraCorrente;
        this.giornata = giornata;
        this.isAdmin = isAdmin;
    }
}
