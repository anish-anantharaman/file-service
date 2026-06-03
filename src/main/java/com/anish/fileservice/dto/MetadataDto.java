package com.anish.fileservice.dto;

import org.springframework.data.annotation.Id;

public record MetadataDto(
        @Id
        String id,

        String name,

        String key,

        String url
) { }