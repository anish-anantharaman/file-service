package com.anish.fileservice.dto;

public record SaveFileDto(
        String key,
        String url // null for private file(s)
) { }