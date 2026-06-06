package com.anish.fileservice.util.swagger;

public class Swagger {

    public static class SwaggerExampleResponses {

        private SwaggerExampleResponses() { }

        public static final String FILE_UPLOAD_SUCCESS = """
                {
                    "statusCode": 201,
                    "statusMessage": "CREATED",
                    "message": "File(s) uploaded successfully",
                    "data": [
                        {
                            "id": "6a23bb41396c4ee65767ff40",
                            "name": "Banner 1.png",
                            "visibility": "public",
                            "url": "https://my-app-files.s3.amazonaws.com/public/84821340-ca3b-4049-9adb-b5fe6c126c5b-Banner1.png"
                        },
                        {
                            "id": "6a23bb41396c4ee65767ff41",
                            "name": "Banner 3.png",
                            "visibility": "public",
                            "url": "https://my-app-files.s3.amazonaws.com/public/f65851a2-608d-47af-af70-e4cf4a80766f-Banner3.png"
                        }
                    ]
                }
            """;

        public static final String FILE_DELETED_SUCCESS = """
                {
                    "statusCode": 200,
                    "statusMessage": "OK",
                    "message": "File(s) deleted successfully",
                    "data": true
                }
            """;

        public static final String PRESIGNED_URL_GENERATION_SUCCESS = """
                {
                    "statusCode": 200,
                    "statusMessage": "OK",
                    "message": "Presigned URL generated successfully",
                    "data": "https://s3.ap-south-1.amazonaws.com/my-app-files/private/db6b3611-cc71-4a20-b3b0-689473a3f5bc-Banner1.png?X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Date=20260606T112247Z&X-Amz-SignedHeaders=host&X-Amz-Credential=AKIAS2D3QDFYTV5R24Y7%2F20260606%2Fap-south-1%2Fs3%2Faws4_request&X-Amz-Expires=900&X-Amz-Signature=d3f119caaf2945c8f169d2f672fc6d84f578119a276f19ad75cd88e125c06bd5"
                }
            """;

        public static final String FILE_METADATA_FETCH_SUCCESS = """
                {
                    "statusCode": 200,
                    "statusMessage": "OK",
                    "message": "Metadata fetched successfully",
                    "data": [
                        {
                            "id": "6a2402db90390ed4475de3e9",
                            "name": "Banner 1.png",
                            "key": "public/db6b3611-cc71-4a20-b3b0-689473a3f5bc-Banner1.png",
                            "url": "https://my-app-files.s3.amazonaws.com/public/db6b3611-cc71-4a20-b3b0-689473a3f5bc-Banner1.png"
                        },
                        {
                            "id": "6a2404a38b8cc160de028c0c",
                            "name": "Banner 1.png",
                            "key": "private/ebb2a2b0-4254-45eb-8d33-14333a95a1e5-Banner1.png",
                            "url": null
                        },
                        {
                            "id": "6a2404a38b8cc160de028c0d",
                            "name": "Banner 3.png",
                            "key": "private/4588a689-7f86-4767-b337-3d3ef445b504-Banner3.png",
                            "url": null
                        }
                    ]
                }
            """;

        public static final String BAD_REQUEST = """
                {
                    "statusCode": 400,
                    "statusMessage": "Bad Request",
                    "message": "fileId : must not be blank;",
                    "data": false
                }
            """;

        public static final String INTERNAL_SERVER_ERROR = """
                {
                  "statusCode": 500,
                  "statusMessage": "Internal Server Error",
                  "message": "An internal error occurred. Please try again later.",
                  "data": false
                }
            """;
    }
}
