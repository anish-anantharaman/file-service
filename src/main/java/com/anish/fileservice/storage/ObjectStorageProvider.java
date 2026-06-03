package com.anish.fileservice.storage;

import com.anish.fileservice.dto.SaveFileDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ObjectStorageProvider {

    SaveFileDto uploadFile(MultipartFile file, boolean isPublic);

    String generatePresignedUrl(String key);

    void deleteFiles(List<String> keys);
}
