package com.anish.fileservice.service;

import com.anish.fileservice.model.Metadata;

import java.util.List;

public interface MetadataService {

    List<Metadata> saveMetadata(List<Metadata> metadata);
}
