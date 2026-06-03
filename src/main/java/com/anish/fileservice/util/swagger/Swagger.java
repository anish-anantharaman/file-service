package com.anish.fileservice.util.swagger;

public class Swagger {

    public static class SwaggerExampleResponses {

        private SwaggerExampleResponses() { }

        public static final String SUCCESS = """
                    {
                        "statusCode": 200,
                        "statusMessage": "Success",
                        "message": "File(s) deleted successfully",
                        "data": true
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
                      "message": "Error in metadata deletion",
                      "data": false
                    }
                """;
    }
}
