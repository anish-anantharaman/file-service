package com.anish.fileservice.service.impl;

import com.anish.fileservice.dto.FileUploadRequestDto;
import com.anish.fileservice.dto.FileUploadResponseDto;
import com.anish.fileservice.dto.MetadataDto;
import com.anish.fileservice.dto.SaveFileDto;
import com.anish.fileservice.exception.MetadataStorageException;
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
    public List<FileUploadResponseDto> addFiles(FileUploadRequestDto fileUploadRequestDto) {
        boolean isPublic = Constants.CommonConstants.PUBLIC.equals(fileUploadRequestDto.visibility())
                ? Boolean.TRUE : Boolean.FALSE;
        List<Metadata> metadata = new ArrayList<>();
        long currentEpochMillis = Instant.now().toEpochMilli();

        for(MultipartFile file : fileUploadRequestDto.files()) {
            SaveFileDto saveFileDto = objectStorageProvider.uploadFile(file, isPublic); // upload files to storage provider

            Metadata data = mapper.mapToMetadata(saveFileDto, file,           // transform data for MongoDB mapping
                    fileUploadRequestDto.visibility(), currentEpochMillis);
            metadata.add(data);
        }
        List<Metadata> metadataResult;
        try {
            metadataResult = metadataService.saveMetadata(metadata);
        } catch (MetadataStorageException e) {
            List<String> uploadedKeys = metadata.stream().map(Metadata::key).toList();
            objectStorageProvider.deleteFiles(uploadedKeys);
            throw e;
        }
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
        List<MetadataDto> metadataDto = metadataService.getMetadataByIds(fileIds); // fetch the files
        List<String> keys = metadataDto.stream()
                .map(MetadataDto::key)
                .toList();
        objectStorageProvider.deleteFiles(keys);   // delete from S3
        List<String> foundIds = metadataDto.stream().map(MetadataDto::id).toList();
        return metadataService.markMetadataDeletedByIds(foundIds); // mark file as deleted in metadata storage
    }

    @Override
    public String generatePresignedUrl(String fileId) {
        List<MetadataDto> metadataDto = metadataService.getMetadataByIds(List.of(fileId));
        if(metadataDto.isEmpty()) {
            throw new MetadataStorageException("No file found with ID=" + fileId);
        }
        return objectStorageProvider.generatePresignedUrl(metadataDto.getFirst().key());
    }

    @Override
    public List<MetadataDto> getMetadataByFileIds(List<String> fileIds) {
        return metadataService.getMetadataByIds(fileIds);
    }

}