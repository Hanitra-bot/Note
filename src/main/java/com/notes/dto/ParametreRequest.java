package com.notes.dto;
import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ParametreRequest {
    @NotNull private Integer idMatiere;
    @NotNull private BigDecimal ecart;
    @NotNull private Integer idOperation;
    @NotNull private Integer idResolution;
}
