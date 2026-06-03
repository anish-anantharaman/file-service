package com.anish.fileservice.storage.s3;

import com.anish.fileservice.config.StorageProviderProperties;
import com.anish.fileservice.dto.SaveFileDto;
import com.anish.fileservice.exception.S3StorageProviderException;
import com.anish.fileservice.storage.ObjectStorageProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.exception.SdkException;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PresignedGetObjectRequest;

import java.io.IOException;
import java.util.List;
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
            log.error("Failed to read uploaded file: {}", e.getMessage(), e);
            throw new S3StorageProviderException("Failed to read uploaded file");
        } catch(SdkException e) {
            log.error("File upload failed: {}", e.getMessage(), e);
            throw new S3StorageProviderException("File upload failed");
        }
    }

    @Override
    public String generatePresignedUrl(String key) {
        try {
            GetObjectRequest objectRequest = GetObjectRequest.builder()
                    .bucket(storageProviderProperties.bucket())
                    .key(key)
                    .build();

            GetObjectPresignRequest presignRequest = GetObjectPresignRequest.builder()
                    .signatureDuration(storageProviderProperties.signedUrlExpiry())
                    .getObjectRequest(objectRequest)
                    .build();
            PresignedGetObjectRequest presignedGetObjectRequest = s3Presigner.presignGetObject(presignRequest);
            return presignedGetObjectRequest.url().toExternalForm();
        } catch (SdkException e) {
            log.error("Presigned URL generation failed: {}", e.getMessage(), e);
            throw new S3StorageProviderException("Presigned URL generation failed");
        }
    }

    @Override
    public void deleteFiles(List<String> keys) {
        try {
            List<ObjectIdentifier> objects = keys.stream()
                    .map(key -> ObjectIdentifier.builder()
                            .key(key)
                            .build())
                    .toList();
            DeleteObjectsRequest deleteObjectsRequest = DeleteObjectsRequest.builder()
                    .bucket(storageProviderProperties.bucket())
                    .delete(Delete.builder()
                            .objects(objects)
                            .build())
                    .build();
            DeleteObjectsResponse deleteResponse = s3Client.deleteObjects(deleteObjectsRequest);
            if (!deleteResponse.errors().isEmpty()) {
                log.error("S3 batch delete partial failure: {}", deleteResponse.errors());
                throw new S3StorageProviderException("One or more files could not be deleted from storage");
            }
        } catch (SdkException e) {
            log.error("File deletion failed: {}", e.getMessage(), e);
            throw new S3StorageProviderException("File deletion failed");
        }
    }

    private String buildPublicUrl(String key) {
        return "https://" + storageProviderProperties.bucket() +
                ".s3.amazonaws.com/" + key;
    }
}
