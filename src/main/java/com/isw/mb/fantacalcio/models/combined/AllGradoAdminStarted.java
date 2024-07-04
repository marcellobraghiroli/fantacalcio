package com.isw.mb.fantacalcio.models.combined;

import com.isw.mb.fantacalcio.models.Allenatore;
import lombok.Getter;

import java.util.List;

@Getter
public class AllGradoAdminStarted {

    private final List<Allenatore> allenatori;
    private final String gradoAdmin;
    private final boolean seasonStarted;

    public AllGradoAdminStarted(List<Allenatore> allenatori, String gradoAdmin, boolean seasonStarted) {
        this.allenatori = allenatori;
        this.gradoAdmin = gradoAdmin;
        this.seasonStarted = seasonStarted;
    }
}
