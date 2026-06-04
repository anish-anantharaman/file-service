package com.anish.fileservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import com.anish.fileservice.annotation.validation.ValidFile;
import jakarta.validation.constraints.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Schema(description = "Request payload for adding one or more files")
public record FileUploadRequestDto(

        @Schema(description = "List of files to upload. " +
                "Multiple files can be provided in a single request.",
                requiredMode = Schema.RequiredMode.REQUIRED)
        @ValidFile
        @Size(max = 10)
        List<@ValidFile MultipartFile> files,

        @Schema(description = "Visibility of the uploaded files. " +
                "'public' files can be accessed using their public URL, " +
                "while 'private' files require authorized access.",
                requiredMode = Schema.RequiredMode.REQUIRED,
                example = "public")
        @NotBlank
        @Pattern(regexp = "public|private", flags = Pattern.Flag.CASE_INSENSITIVE)
        String visibility
) { }