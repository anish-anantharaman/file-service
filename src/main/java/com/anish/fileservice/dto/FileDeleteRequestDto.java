package com.anish.fileservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

@Schema(description = "Request payload for deleting one or more files")
public record FileDeleteRequestDto(

        @Schema(description = "Unique file IDs to delete",
        example = "[\"6a240c597757062ff26cbc7a\"]",
        requiredMode = Schema.RequiredMode.REQUIRED)
        @Size(min = 1, max = 10)
        List<@NotBlank String> fileIds
) { }