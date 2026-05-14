package com.anish.fileservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Schema(description = "Request dto for adding new file(s)")
public record FileRequestDto(

        @Schema(description = "List of files to upload. Multiple files can be provided in a single request.")
        @NotEmpty
        List<@NotNull MultipartFile> files,

        @Schema(description = "", example = "public")
        @NotBlank
        @Pattern(regexp = "public|private", flags = Pattern.Flag.CASE_INSENSITIVE)
        String visibility
) { }