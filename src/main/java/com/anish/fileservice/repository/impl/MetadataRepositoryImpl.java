package com.anish.fileservice.repository.impl;

import com.anish.fileservice.dto.MetadataDto;
import com.anish.fileservice.exception.MetadataStorageException;
import com.anish.fileservice.model.Metadata;
import com.anish.fileservice.repository.MetadataRepository;
import com.anish.fileservice.util.Constants;
import com.mongodb.client.result.UpdateResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
@Slf4j
@RequiredArgsConstructor
public class MetadataRepositoryImpl implements MetadataRepository {

    private final MongoTemplate mongoTemplate;

    @Override
    public List<Metadata> saveMetadata(List<Metadata> metadata) {
        try {
            return (List<Metadata>) mongoTemplate.insert(metadata, Metadata.class);
        } catch(DataAccessException e) {
            log.error("Error saving metadata: {}", e.getMessage(), e);
            throw new MetadataStorageException("Error saving file metadata");
        }
    }

    @Override
    public boolean markMetadataDeletedByIds(List<String> ids) {
        try {
            Query query = Query.query(
                    Criteria.where(Constants.MongoConstants.ID).in(ids)
                            .and(Constants.CommonConstants.DELETED_AT_MILLIS).is(null)
            );
            Update update = new Update()
                    .set(Constants.CommonConstants.DELETED_AT_MILLIS, Instant.now().toEpochMilli());
            UpdateResult updateResult = mongoTemplate.updateMulti(query, update, Metadata.class);
            return updateResult.getModifiedCount() == ids.size();
        } catch (DataAccessException e) {
            log.error("Error updating metadata for deleted file(s)");
            throw new MetadataStorageException("Error in metadata deletion");
        }
    }

    @Override
    public List<MetadataDto> getMetadataByIds(List<String> ids) {
        try {
            Query query = Query.query(
                    Criteria.where(Constants.MongoConstants.ID)
                            .in(ids)
                            .and(Constants.CommonConstants.DELETED_AT_MILLIS)
                            .is(null)
            );
            return mongoTemplate.find(query, MetadataDto.class, "metadata");
        } catch (DataAccessException e) {
            log.error("Error fetching metadata for IDs={}: {}", ids, e.getMessage(), e);
            throw new MetadataStorageException("Error fetching metadata");
        }
    }
}