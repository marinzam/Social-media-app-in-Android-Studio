package project02.csc214.coucou.Database;

/**
 * Created by geraldine
 */

public class DBSchema {
    public static final String DATABASE_NAME = "project02.db";
    public static final int VERSION = 1;

    public static class UserTable {
        public static final String NAME = "users";
        public static class Cols {
            public static final String USER_NAME = "user_name";
            public static final String EMAIL = "email";
            public static final String PASSWORD = "password";
            public static final String FULL_NAME = "full_name";
            public static final String BIRTH_DATE = "birth_date";
            public static final String HOME_TOWN = "home_town";
            public static final String PROFILE_PIC = "profile_pic";
            public static final String SHORT_BIO = "short_bio";
        }
    }

    public static class FeedItemsTable {
        public static final String NAME = "feed_items";
        public static class Cols {
            public static final String ID = "id";
            public static final String USER_NAME = "user_name";
            public static final String USER_PIC = "user_pic";
            public static final String POSTED_DATE = "posted_date";
            public static final String CONTENT = "content";
            public static final String PHOTO_PATH = "photo_path";
            public static final String STARRED = "starred";
        }
    }

    public static class FavoritesTable {
        public static final String NAME = "favorites";
        public static class Cols {
            public static final String USER_NAME = "user_name";
            public static final String FAVORITE = "favorite";
        }
    }
    public static class LoginsTable {
        public static final String NAME = "logins";
        public static class Cols {
            public static final String USER_NAME = "user_name";
            public static final String LOGGED_IN = "logged_in";
        }
    }
}
