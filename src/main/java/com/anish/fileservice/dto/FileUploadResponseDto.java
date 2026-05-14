package com.anish.fileservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Response DTO representing metadata and access details of a successfully uploaded file.")
public record FileUploadResponseDto(

        @Schema(
                description = "Unique identifier of the uploaded file.",
                example = "5ba085d5-8930-466d-a342-995b058c49e7"
        )
        String id,

        @Schema(
                description = "Original name of the uploaded file.",
                example = "user_data.pdf"
        )
        String name,

        @Schema(
                description = "Visibility level of the file. Determines access control (e.g., public or private).",
                example = "public"
        )
        String visibility,

        @Schema(
                description = "Direct access URL of the file. Available only when visibility is set to 'public'. " +
                        "For private files, use the appropriate API to generate a presigned URL.",
                example = "https://my-bucket.s3.ap-south-1.amazonaws.com/uploads/user_data-77sb-sjah-qb9f.pdf"
        )
        String url
) { }