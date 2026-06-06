package com.anish.fileservice.controller;

import com.anish.fileservice.annotation.swagger.ErrorResponses;
import com.anish.fileservice.dto.*;
import com.anish.fileservice.service.FileService;
import com.anish.fileservice.util.swagger.Swagger;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1")
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    @ApiResponse(
            responseCode = "201",
            description = "File(s) uploaded successfully",
            headers = @Header(
                    name = "requestId",
                    description = "Unique ID for tracking this request",
                    schema = @Schema(type = "string", example = "c828bdd1-2fa6-4d58-8d25-8bb7fa9cdc1f")
            ),
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ApiResponseDto.class),
                    examples = @ExampleObject(value = Swagger.SwaggerExampleResponses.FILE_UPLOAD_SUCCESS)
            )
    )
    @ErrorResponses
    @Operation(
            summary = "Upload files",
            description = "Uploads up to 10 files to cloud storage and returns metadata for each uploaded file. Public files include an accessible URL."
    )
    @PostMapping(path = "/files", consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> addFiles(@Valid @ModelAttribute FileUploadRequestDto fileUploadRequestDto) {
        List<FileUploadResponseDto> response = fileService.addFiles(fileUploadRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(
                new ApiResponseDto(
                        HttpStatus.CREATED.value(),
                        HttpStatus.CREATED.getReasonPhrase(),
                        "File(s) uploaded successfully",
                        response
                )
        );
    }

    @ApiResponse(
            responseCode = "200",
            description = "File(s) deleted successfully",
            headers = @Header(
                    name = "requestId",
                    description = "Unique ID for tracking this request",
                    schema = @Schema(type = "string", example = "c828bdd1-2fa6-4d58-8d25-8bb7fa9cdc1f")
            ),
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ApiResponseDto.class),
                    examples = @ExampleObject(value = Swagger.SwaggerExampleResponses.FILE_DELETED_SUCCESS)
            )
    )
    @ErrorResponses
    @Operation(
            summary = "Delete files",
            description = "Deletes up to 10 files from cloud storage using their file IDs."
    )
    @DeleteMapping(path = "/files", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> deleteFiles(@Valid @RequestBody FileDeleteRequestDto fileDeleteRequestDto) {
        boolean response = fileService.deleteFiles(fileDeleteRequestDto.fileIds());
        return ResponseEntity.ok().body(new ApiResponseDto(
                HttpStatus.OK.value(),
                HttpStatus.OK.getReasonPhrase(),
                "File(s) deleted successfully",
                response
        ));
    }

    @ApiResponse(
            responseCode = "200",
            description = "Presigned URL generated successfully",
            headers = @Header(
                    name = "requestId",
                    description = "Unique ID for tracking this request",
                    schema = @Schema(type = "string", example = "c828bdd1-2fa6-4d58-8d25-8bb7fa9cdc1f")
            ),
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ApiResponseDto.class),
                    examples = @ExampleObject(value = Swagger.SwaggerExampleResponses.PRESIGNED_URL_GENERATION_SUCCESS)
            )
    )
    @ErrorResponses
    @Operation(
            summary = "Generate a presigned URL",
            description = "Generates a temporary presigned URL for accessing a file identified by its file ID."
    )
    @GetMapping(path = "/files/{fileId}/presigned-url", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> generatePresignedUrl(@PathVariable String fileId) {
        String response = fileService.generatePresignedUrl(fileId);
        return ResponseEntity.ok().body(new ApiResponseDto(
                HttpStatus.OK.value(),
                HttpStatus.OK.getReasonPhrase(),
                "Presigned URL generated successfully",
                response
        ));
    }

    @ApiResponse(
            responseCode = "200",
            description = "Metadata fetched successfully",
            headers = @Header(
                    name = "requestId",
                    description = "Unique ID for tracking this request",
                    schema = @Schema(type = "string", example = "c828bdd1-2fa6-4d58-8d25-8bb7fa9cdc1f")
            ),
            content = @Content(
                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                    schema = @Schema(implementation = ApiResponseDto.class),
                    examples = @ExampleObject(value = Swagger.SwaggerExampleResponses.FILE_METADATA_FETCH_SUCCESS)
            )
    )
    @ErrorResponses
    @Operation(
            summary = "Fetch file metadata",
            description = "Retrieves metadata for up to 10 files using their file IDs."
    )
    @PostMapping(path = "/files/metadata", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> getMetadataByFileIds(@Valid @RequestBody FileMetadataRequestDto fileMetadataRequestDto) {
        List<MetadataDto> response = fileService.getMetadataByFileIds(fileMetadataRequestDto.fileIds());
        return ResponseEntity.ok().body(new ApiResponseDto(
                HttpStatus.OK.value(),
                HttpStatus.OK.getReasonPhrase(),
                "Metadata fetched successfully",
                response
        ));
    }
}
