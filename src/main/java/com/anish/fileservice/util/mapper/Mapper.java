package com.anish.fileservice.util.mapper;

import com.anish.fileservice.dto.SaveFileDto;
import com.anish.fileservice.model.Metadata;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class Mapper {

    public Metadata mapToMetadata(SaveFileDto saveFileDto, MultipartFile file,
                                  String visibility, long currentEpochMillis) {
        return new Metadata(null, file.getOriginalFilename(),
                file.getContentType(),
                file.getSize(),
                visibility,
                saveFileDto.key(),
                saveFileDto.url(),
                currentEpochMillis,
                null
        );
    }
}