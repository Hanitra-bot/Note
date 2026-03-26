package com.notes.dto;
import jakarta.validation.constraints.*;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class CandidatRequest {
    @NotBlank private String nom;
}
