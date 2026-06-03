package com.anish.fileservice.service;

import com.anish.fileservice.dto.FileUploadRequestDto;
import com.anish.fileservice.dto.FileUploadResponseDto;
import com.anish.fileservice.dto.MetadataDto;

import java.util.List;

public interface FileService {

    List<FileUploadResponseDto> addFiles(FileUploadRequestDto fileUploadRequestDto);

    boolean deleteFiles(List<String> fileIds);

    String generatePresignedUrl(String fileId);

    List<MetadataDto> getMetadataByFileIds(List<String> fileIds);
}
