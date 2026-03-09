package com.notes.dto;

import lombok.*;
import java.math.BigDecimal;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class NoteFinaleResultDTO {

    private Integer candidatId;
    private String  candidatNom;

    private Integer matiereId;
    private String  matiereLibelle;

    private List<BigDecimal> notes;
    private List<DiffDetail> details;
    private BigDecimal totalDiff;

    private BigDecimal parametreEcart;
    private String     operation;
    private String     resolution;
    private boolean    parametreApplicable;

    private BigDecimal noteMax;
    private BigDecimal noteMin;
    private BigDecimal noteMoyenne;
    private BigDecimal noteFinaleCalculee;
    private boolean    sauvegardee;

    @Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
    public static class DiffDetail {
        private BigDecimal note1;
        private BigDecimal note2;
        private BigDecimal difference;
    }
}
