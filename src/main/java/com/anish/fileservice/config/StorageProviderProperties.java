package com.anish.fileservice.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "storage")
public record StorageProviderProperties(
        String backend,
        String bucket,
        String region,
        String accessKey,
        String secretKey
) { }