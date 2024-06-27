package com.isw.mb.fantacalcio.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "codice")
public class Codice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idcodice", nullable = false)
    private Integer id;

    @Column(name = "ultimo_codice", nullable = false, columnDefinition = "CHAR(6)")
    private String ultimoCodice;

}
