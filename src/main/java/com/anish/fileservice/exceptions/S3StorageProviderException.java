package com.anish.fileservice.exceptions;

public class S3StorageProviderException extends RuntimeException {

    public S3StorageProviderException(String message) {
        super(message);
    }
}