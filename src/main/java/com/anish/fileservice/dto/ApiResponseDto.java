package com.anish.fileservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Standard API response wrapper")
public record ApiResponseDto(

        @Schema(description = "HTTP status code of the response", example = "200")
        int statusCode,

        @Schema(description = "HTTP status message", example = "OK")
        String statusMessage,

        @Schema(description = "Custom message describing the result", example = "File(s) uploaded successfully")
        String message,

        @Schema(description = "Payload of the response, can be any type")
        Object data
) { }