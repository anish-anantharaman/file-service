package com.anish.fileservice.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;

@Configuration
@RequiredArgsConstructor
public class StorageProviderConfig {

    private final StorageProviderProperties storageProviderProperties;

    // Configuration for Amazon S3
    @Bean
    public S3Client s3Client() {
        AwsBasicCredentials awsBasicCredentials =
                AwsBasicCredentials.create(storageProviderProperties.accessKey(),
                        storageProviderProperties.secretKey());
        return S3Client.builder()
                .region(Region.of(storageProviderProperties.region()))
                .credentialsProvider(StaticCredentialsProvider.create(awsBasicCredentials))
                .build();
    }

    @Bean
    public S3Presigner s3Presigner() {
        AwsBasicCredentials awsBasicCredentials =
                AwsBasicCredentials.create(storageProviderProperties.accessKey(),
                        storageProviderProperties.secretKey());
        return S3Presigner.builder()
                .region(Region.of(storageProviderProperties.region()))
                .credentialsProvider(StaticCredentialsProvider.create(awsBasicCredentials))
                .build();
    }
}