package com.anish.fileservice.exception;

public class S3StorageProviderException extends RuntimeException {

    public S3StorageProviderException(String message) {
        super(message);
    }
}