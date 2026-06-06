package com.anish.fileservice.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Standard API response wrapper")
public record ApiResponseDto(

        @Schema(description = "HTTP status code of the response", example = "200")
        int statusCode,

        @Schema(description = "HTTP status message", example = "OK")
        String statusMessage,

        @Schema(description = "Custom message describing the result", example = "File(s) uploaded successfully")
        String message,

        @Schema(description = "Payload of the response, can be any type",
                example = """
                        {
                            "statusCode": 200,
                            "statusMessage": "OK",
                            "message": "File(s) uploaded successfully",
                            "data": [
                                {
                                    "id": "6a21406ad1e930f9f85d6c3d",
                                    "name": "Banner 1.png",
                                    "visibility": "public",
                                    "url": "https://my-app-files.s3.amazonaws.com/public/12f5f140-4637-4be4-b603-f04a4d0adc28-Banner1.png"
                                },
                                {
                                    "id": "6a21406ad1e930f9f85d6c3e",
                                    "name": "Banner 3.png",
                                    "visibility": "public",
                                    "url": "https://my-app-files.s3.amazonaws.com/public/e2e75450-2907-4150-8592-6d5170cb1949-Banner3.png"
                                }
                            ]
                        }
                """)
        Object data
) { }