package com.anish.fileservice.repository;

import com.anish.fileservice.dto.MetadataDto;
import com.anish.fileservice.model.Metadata;

import java.util.List;

public interface MetadataRepository {

    List<Metadata> saveMetadata(List<Metadata> metadata);

    boolean markMetadataDeletedByIds(List<String> ids);

    List<MetadataDto> getMetadataByIds(List<String> ids);
}