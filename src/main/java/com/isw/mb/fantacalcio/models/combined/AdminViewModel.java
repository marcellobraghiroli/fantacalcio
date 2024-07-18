package com.isw.mb.fantacalcio.models.combined;

import com.isw.mb.fantacalcio.models.Allenatore;
import com.isw.mb.fantacalcio.models.Giornata;
import lombok.Getter;

import java.util.List;

@Getter
public class AdminViewModel {

    private final List<Allenatore> allenatori;
    private final String gradoAdmin;
    private final boolean seasonStarted;
    private final List<Giornata> giornate;

    public AdminViewModel(List<Allenatore> allenatori, String gradoAdmin, boolean seasonStarted, List<Giornata> giornate) {
        this.allenatori = allenatori;
        this.gradoAdmin = gradoAdmin;
        this.seasonStarted = seasonStarted;
        this.giornate = giornate;
    }
}
