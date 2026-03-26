package com.notes.dto;
import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class NoteRequest {
    @NotNull private Integer idMatiere;
    @NotNull private Integer idCandidat;
    @NotNull private Integer idCorrecteur;
    @DecimalMin("0.00") @DecimalMax("20.00") @NotNull private BigDecimal note;
}
