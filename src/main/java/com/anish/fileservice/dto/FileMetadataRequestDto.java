package com.anish.fileservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.List;

public record FileMetadataRequestDto(
        @NotEmpty @Size(min = 1, max = 10) List<@NotBlank String> fileIds
) { }