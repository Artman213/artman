package com.example.artman2111.test.db;

/**
 * Created by artman2111 on 26.05.17.
 */

public class DataBaseConstans {
    public static final String DB_NAME = "db_users";
    public static final int DB_VERSION = 1;

    public final static class DB_V1{
        public final static class TABLE_USER_INFO{
            public static final String TABLE_NAME = "UserInfo";

            public static final String USER_INFO_FIELD_NAME_ID = "_id";
            public static final String USER_INFO_FIELD_LAST_LOGIN = "_last_login";
            public static final String USER_INFO_FIELD_USERNAME = "_username";
        }
    }

}
