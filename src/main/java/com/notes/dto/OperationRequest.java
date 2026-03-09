package com.notes.dto;
import jakarta.validation.constraints.*;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class OperationRequest {
    @NotBlank private String signe;
}
