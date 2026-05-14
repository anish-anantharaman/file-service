package com.anish.fileservice.service.impl;

import com.anish.fileservice.dto.FileRequestDto;
import com.anish.fileservice.dto.FileUploadResponseDto;
import com.anish.fileservice.dto.SaveFileDto;
import com.anish.fileservice.model.Metadata;
import com.anish.fileservice.service.FileService;
import com.anish.fileservice.service.MetadataService;
import com.anish.fileservice.storage.ObjectStorageProvider;
import com.anish.fileservice.util.Constants;
import com.anish.fileservice.util.mapper.Mapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FileServiceImpl implements FileService {

    private final ObjectStorageProvider objectStorageProvider;
    private final MetadataService metadataService;
    private final Mapper mapper;

    @Override
    public List<FileUploadResponseDto> addFiles(FileRequestDto fileRequestDto) {
        boolean isPublic = Constants.CommonConstants.PUBLIC.equals(fileRequestDto.visibility())
                ? Boolean.TRUE : Boolean.FALSE;
        List<Metadata> metadata = new ArrayList<>();
        long currentEpochMillis = Instant.now().toEpochMilli();

        for(MultipartFile file : fileRequestDto.files()) {
            // upload files to storage provider
            SaveFileDto saveFileDto = objectStorageProvider.uploadFile(file, isPublic);
            // transform data for MongoDB mapping
            Metadata data = mapper.mapToMetadata(saveFileDto, file,
                    fileRequestDto.visibility(), currentEpochMillis);
            metadata.add(data);
        }
        List<Metadata> metadataResult = metadataService.saveMetadata(metadata);
        return metadataResult.stream()
                .map(meta -> new FileUploadResponseDto(
                        meta.id(),
                        meta.name(),
                        meta.visibility(),
                        meta.url()
                )).toList();
    }

    @Override
    public boolean deleteFiles(List<String> fileIds) {
        return false;
    }
}
