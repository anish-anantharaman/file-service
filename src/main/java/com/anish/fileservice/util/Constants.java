package com.anish.fileservice.util;

public class Constants {

    private Constants() { }

    public static final class CommonConstants {

        private CommonConstants() { }

        public static final String PUBLIC = "public";
        public static final String DELETED_AT = "deletedAt";
        public static final String REQUEST_ID_HEADER = "X-RequestId";
    }

    public static final class MongoConstants {

        private MongoConstants() { }

        public static final String ID = "_id";
    }

}
