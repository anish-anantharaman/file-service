package com.anish.fileservice.controller;

import com.anish.fileservice.annotation.swagger.CommonErrorResponses;
import com.anish.fileservice.annotation.swagger.SaveFileSuccessResponse;
import com.anish.fileservice.dto.*;
import com.anish.fileservice.service.FileService;
import io.swagger.v3.oas.annotations.Operation;
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

    @CommonErrorResponses
    @SaveFileSuccessResponse
    @Operation
    @PostMapping(path = "/files", consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> addFiles(@Valid @ModelAttribute FileUploadRequestDto fileUploadRequestDto) {
        List<FileUploadResponseDto> response = fileService.addFiles(fileUploadRequestDto);
        return ResponseEntity.ok().body(new ApiResponseDto(
                HttpStatus.OK.value(),
                HttpStatus.OK.getReasonPhrase(),
                "File(s) uploaded successfully",
                response
        ));
    }

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