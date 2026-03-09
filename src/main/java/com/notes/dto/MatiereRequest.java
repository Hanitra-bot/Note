package com.notes.dto;
import jakarta.validation.constraints.*;
import lombok.*;
import java.util.List;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class MatiereRequest {
    @NotBlank private String libelle;
    private List<Integer> correcteurIds;
}
