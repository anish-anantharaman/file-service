package com.anish.fileservice.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;
@ConfigurationProperties(prefix = "storage")
public record StorageProviderProperties(
        String provider,
        String bucket,
        String region,
        String accessKey,
        String secretKey,
        String signedUrlExpiry
) { }