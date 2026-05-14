package com.anish.fileservice.controller;

import com.anish.fileservice.dto.ApiResponseDto;
import com.anish.fileservice.dto.FileRequestDto;
import com.anish.fileservice.dto.FileUploadResponseDto;
import com.anish.fileservice.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1")
@RequiredArgsConstructor
public class FileController {


    private final FileService fileService;

//    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
//            produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<Object> addFiles(
//            @NotEmpty(message = "At least one file is required.")
//            @RequestPart(name = "files") List<MultipartFile> files,
//
//            @NotBlank
//            @Pattern(regexp = "public|private", flags = Pattern.Flag.CASE_INSENSITIVE)
//            @RequestPart("visibility") String visibility) {
//        return null;
//    }

    @PostMapping(path = "/files", consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> addFiles(@ModelAttribute FileRequestDto fileRequestDto) {
        List<FileUploadResponseDto> responseDto = fileService.addFiles(fileRequestDto);
        return ResponseEntity.ok().body(new ApiResponseDto(
                HttpStatus.OK.value(),
                HttpStatus.OK.getReasonPhrase(),
                "File(s) uploaded successfully",
                responseDto
        ));
    }

}
