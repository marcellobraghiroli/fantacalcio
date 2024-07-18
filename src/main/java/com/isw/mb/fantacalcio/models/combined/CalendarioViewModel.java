package com.isw.mb.fantacalcio.models.combined;

import com.isw.mb.fantacalcio.models.Giornata;
import com.isw.mb.fantacalcio.models.Partita;
import lombok.Getter;

import java.util.List;

@Getter
public class CalendarioViewModel {

    private final List<Partita> partite;
    private final Giornata giornataCorrente;

    public CalendarioViewModel(List<Partita> partite, Giornata giornataCorrente) {
        this.partite = partite;
        this.giornataCorrente = giornataCorrente;
    }
}
