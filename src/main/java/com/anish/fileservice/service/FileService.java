package com.anish.fileservice.service;

import com.anish.fileservice.dto.FileRequestDto;
import com.anish.fileservice.dto.FileUploadResponseDto;

import java.util.List;

public interface FileService {

    List<FileUploadResponseDto> addFiles(FileRequestDto fileRequestDto);

    boolean deleteFiles(List<String> fileIds);
}
