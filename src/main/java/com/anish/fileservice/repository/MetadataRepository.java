package com.anish.fileservice.repository;

import com.anish.fileservice.model.Metadata;

import java.util.List;
import java.util.Optional;

public interface MetadataRepository {

    List<Metadata> saveMetadata(List<Metadata> metadata);

    Optional<Metadata> getMetadataById(String id);

    long markMetadataDeletedByIds(List<String> ids);
}