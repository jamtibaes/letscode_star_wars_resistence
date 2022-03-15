package com.letscode.starwar.dto;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MensagemResponseDTO {
    private String message;
}
