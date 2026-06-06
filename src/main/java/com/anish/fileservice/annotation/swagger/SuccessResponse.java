package com.anish.fileservice.annotation.swagger;

import com.anish.fileservice.dto.ApiResponseDto;
import io.swagger.v3.oas.annotations.headers.Header;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;

import java.lang.annotation.*;

@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@ApiResponse(responseCode = "200",
        description = "File(s) uploaded successfully",
        headers = {
                @Header(
                        name = "requestId",
                        description = "Unique ID for tracking this request",
                        schema = @Schema(type = "string", example = "c828bdd1-2fa6-4d58-8d25-8bb7fa9cdc1f")
                )
        },
        content = @Content(
                mediaType = MediaType.APPLICATION_JSON_VALUE,
                schema = @Schema(implementation = ApiResponseDto.class)
        ))
public @interface SuccessResponse {
    String example() default "";
}
