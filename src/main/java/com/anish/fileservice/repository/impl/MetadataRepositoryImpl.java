package com.anish.fileservice.repository.impl;

import com.anish.fileservice.exceptions.MetadataStorageException;
import com.anish.fileservice.model.Metadata;
import com.anish.fileservice.repository.MetadataRepository;
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
import java.util.Optional;

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
    public Optional<Metadata> getMetadataById(String id) {
        try {
            Query query = Query.query(
                    Criteria.where("_id")
                            .is(id)
                            .and("deletedAt")
                            .is(null)
            );
            Metadata metadata = mongoTemplate.findById(query, Metadata.class);
            return Optional.ofNullable(metadata);
        } catch (DataAccessException e) {
            log.error("Error fetching metadata for id={}: {}", id, e.getMessage(), e);
            throw new MetadataStorageException("Error fetching metadata");
        }
    }

    @Override
    public long markMetadataDeletedByIds(List<String> ids) {
        try {
            Query query = Query.query(
                    Criteria.where("_id").in(ids)
                            .and("deletedAt").is(null)
            );
            Update update = new Update()
                    .set("deletedAt", Instant.now().toEpochMilli());

            UpdateResult updateResult = mongoTemplate.updateMulti(query, update, Metadata.class);
            return updateResult.getModifiedCount();
        } catch (DataAccessException e) {
            log.error("Error updating metadata for deleted file(s)");
            throw new MetadataStorageException("Error in metadata deletion");
        }
    }
}