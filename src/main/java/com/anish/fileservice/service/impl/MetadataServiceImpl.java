package com.anish.fileservice.service.impl;

import com.anish.fileservice.model.Metadata;
import com.anish.fileservice.repository.MetadataRepository;
import com.anish.fileservice.service.MetadataService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MetadataServiceImpl implements MetadataService {

    private final MetadataRepository metadataRepository;

    @Override
    public List<Metadata> saveMetadata(List<Metadata> metadata) {
        return metadataRepository.saveMetadata(metadata);
    }
}
