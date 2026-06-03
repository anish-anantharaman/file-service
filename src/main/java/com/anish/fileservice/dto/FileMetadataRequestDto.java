package com.anish.fileservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record FileMetadataRequestDto(
        @NotEmpty List<@NotBlank String> fileIds
) { }