package com.anish.fileservice.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "metadata")
public record Metadata(
        @Id
        String id,

        String name,

        String contentType,

        long sizeInBytes,

        String visibility,

        String key,

        String url,

        long createdAtMillis,

        Long deletedAtMillis
) { }