package com.notes.dto;
import jakarta.validation.constraints.*;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ResolutionRequest {
    @NotBlank private String libelle;
}
