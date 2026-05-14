package com.anish.fileservice.storage.s3;

import com.anish.fileservice.config.StorageProviderProperties;
import com.anish.fileservice.dto.SaveFileDto;
import com.anish.fileservice.exceptions.S3StorageProviderException;
import com.anish.fileservice.storage.ObjectStorageProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class S3StorageProvider implements ObjectStorageProvider {

    private final StorageProviderProperties storageProviderProperties;
    private final S3Client s3Client;
    private final S3Presigner s3Presigner;


    @Override
    public SaveFileDto uploadFile(MultipartFile file, boolean isPublic) {
        try {
            String visibility = isPublic ? "public" : "private";
            String key = String.format("%s/%s-%s", visibility,
                    UUID.randomUUID(), file.getOriginalFilename());

            PutObjectRequest.Builder requestBuilder = PutObjectRequest.builder()
                    .bucket(storageProviderProperties.bucket())
                    .key(key)
                    .contentType(file.getContentType());
            if(isPublic) {
                requestBuilder.acl("public-read");
            }

            s3Client.putObject(requestBuilder.build(),
                    RequestBody.fromInputStream(file.getInputStream(), file.getSize()));
            String url = isPublic ? buildPublicUrl(key) : null;
            return new SaveFileDto(key, url);
        } catch(IOException e) {
            log.error("Error uploading file to S3: {}", e.getMessage(), e);
            throw new S3StorageProviderException("Error uploading file to S3");
        }
    }

    @Override
    public String generatePresignedUrl(String key) {
        return null;
    }

    private String buildPublicUrl(String key) {
        return "https://" + storageProviderProperties.bucket() +
                ".s3.amazonaws.com/" + key;
    }
}
