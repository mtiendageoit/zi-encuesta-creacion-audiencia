package com.zonainmueble.surveys.clients;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class EmailAttachFile {
    @NotBlank
    private String filename;

    @NotEmpty
    private byte[] file;
}
