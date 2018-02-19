package project02.csc214.coucou;
/*
* Problems: Do I add more methods to get individual items in a user?
*/
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import project02.csc214.coucou.Database.DBOpenHelper;
import project02.csc214.coucou.Database.DBSchema;
import project02.csc214.coucou.Model.Favorite;
import project02.csc214.coucou.Model.FeedItem;
import project02.csc214.coucou.Model.Login;
import project02.csc214.coucou.Model.User;

import static java.lang.Integer.valueOf;

/**
 * Created by Geraldine
 */

public class DataAccessObject {

    private static DataAccessObject SINGLETON;

    private final SQLiteDatabase mDatabase;

    //private final List<User> mUsers;
    private final List<FeedItem> mFeedItems;
    //private final List<Favorite> mFavorites;

   public DataAccessObject(Context context) {
       // open a connection to database
       mDatabase = new DBOpenHelper(context).getWritableDatabase();
       mFeedItems = new LinkedList<>();
    }

    public static DataAccessObject get(Context context) {
        if(SINGLETON == null) {
            SINGLETON = new DataAccessObject(context);
        }
        return SINGLETON;
    }

    public User getUser(String username, String password) {
        Cursor cursor = mDatabase.query(
                DBSchema.UserTable.NAME, // table name
                null,
                "user_name = ? AND password = ?",
                new String[] { username, password},
                null,
                null,
                null
        );

        UserCursorWrapper wrapper = new UserCursorWrapper(cursor);
        User user;
        if(wrapper.getCount() > 0) {
            wrapper.moveToFirst();
            user = wrapper.getUser();
        }
        else {
            user = null;
        }
        wrapper.close();

        return user;
    }

    public User getUserFromUserName(String username) {
        String Query =
                "Select * from " + DBSchema.UserTable.NAME +
                        " where " + DBSchema.UserTable.Cols.USER_NAME +
                        " = \"" + username + "\";";
        Cursor cursor = mDatabase.rawQuery(Query, null);

        UserCursorWrapper wrapper = new UserCursorWrapper(cursor);
        User user;
        if(wrapper.getCount() > 0) {
            wrapper.moveToFirst();
            user = wrapper.getUser();
        }
        else {
            user = null;
        }
        wrapper.close();

        return user;
    }
    public User getUserFromEmail(String email) {
        String Query =
                "Select * from " + DBSchema.UserTable.NAME +
                        " where " + DBSchema.UserTable.Cols.EMAIL +
                        " = \"" + email + "\";";
        Cursor cursor = mDatabase.rawQuery(Query, null);
        UserCursorWrapper wrapper = new UserCursorWrapper(cursor);
        User user;
        if(wrapper.getCount() > 0) {
            wrapper.moveToFirst();
            user = wrapper.getUser();
        }
        else {
            user = null;
        }
        wrapper.close();

        return user;
    }

    public void insertUser(User user) {
        ContentValues values = getUserContentValues(user);
        mDatabase.insert(
                DBSchema.UserTable.NAME,
                null,
                values
        );
    }

    public void updateUser(User user) {
        String userName = user.getUserName();
        ContentValues values = getUserContentValues(user);
        mDatabase.update(DBSchema.UserTable.NAME,
                values,
                DBSchema.UserTable.Cols.USER_NAME + "=?",
                new String[]{userName});
    }

    /*
    public List<User> getUsers() {
        mUsers.clear();
        String Query =
                "Select * from " + DBSchema.FavoritesTable.NAME +
                        " where " + DBSchema.FavoritesTable.Cols.USER_NAME +
                        " = ? ;";
        Cursor cursor = mDatabase.rawQuery(Query, null);

        UserCursorWrapper wrapper = new UserCursorWrapper(cursor);

        try {
            wrapper.moveToFirst();
            while(wrapper.isAfterLast() == false) {
                User item = wrapper.getUser();
                mUsers.add(item);
                wrapper.moveToNext();
            }
        }
        finally {
            wrapper.close();
        }
        return mUsers;
    }

*/
    public FeedItem getFeedItem(String id) {
        String Query =
                "Select * from " + DBSchema.FeedItemsTable.NAME +
                        " where " + DBSchema.FeedItemsTable.Cols.ID +
                        " = \"" + id + "\";";
        Cursor cursor = mDatabase.rawQuery(Query, null);

        FeedItemCursorWrapper wrapper = new FeedItemCursorWrapper(cursor);
        FeedItem item;
        if(wrapper.getCount() > 0) {
            wrapper.moveToFirst();
            item = wrapper.getFeedItem();
        }
        else {
            item = null;
        }
        wrapper.close();

        return item;
    }


    public void insertFeedItem(FeedItem item) {
        ContentValues values = getFeedItemContentValues(item);
        mDatabase.insert(
                DBSchema.FeedItemsTable.NAME,
                null,
                values
        );
    }

    public List<FeedItem> getFeedItems() {
        mFeedItems.clear();
        String Query =
                "Select * from " + DBSchema.FeedItemsTable.NAME +
                        " where " + DBSchema.FeedItemsTable.Cols.ID +
                        " = ? ;";
        Cursor cursor = mDatabase.rawQuery(Query, null);

        FeedItemCursorWrapper wrapper = new FeedItemCursorWrapper(cursor);

        try {
            wrapper.moveToFirst();
            while(wrapper.isAfterLast() == false) {
                FeedItem item = wrapper.getFeedItem();
                mFeedItems.add(item);
                wrapper.moveToNext();
            }
        }
        finally {
            wrapper.close();
        }

        return mFeedItems;
    }

    public Favorite getFavorite(String username) {
        String Query =
                "Select * from " + DBSchema.FavoritesTable.NAME +
                " where " + DBSchema.FavoritesTable.Cols.USER_NAME +
                " = " + username + ";";

        Cursor cursor = mDatabase.rawQuery(Query, null);

        FavoriteCursorWrapper wrapper = new FavoriteCursorWrapper(cursor);
        Favorite fav;
        if(wrapper.getCount() > 0) {
            wrapper.moveToFirst();
            fav = wrapper.getFavorite(username);
        }
        else {
            fav = null;
        }
        wrapper.close();

        return fav;
    }

    public void insertFavorite(Favorite favorite) {
        ContentValues values = getFavoriteContentValues(favorite);
        mDatabase.insert(
                DBSchema.FavoritesTable.NAME,
                null,
                values
        );
    }
/*
    public List<Favorite> getFavorites(String username) {
        mFavorites.clear();
        String Query =
                "Select * from " + DBSchema.FavoritesTable.NAME +
                        " where " + DBSchema.FavoritesTable.Cols.USER_NAME +
                        " = "+ username+ " ;";
        Cursor cursor = mDatabase.rawQuery(Query, null);

        FavoriteCursorWrapper wrapper = new FavoriteCursorWrapper(cursor);

        try {
            wrapper.moveToFirst();
            while(wrapper.isAfterLast() == false) {
                Favorite item = wrapper.getFavorite(username);
                mFavorites.add(item);
                wrapper.moveToNext();
            }
        }
        finally {
            wrapper.close();
        }
        return mFavorites;
    }
*/
    public Login getRecentLogin() {
        String Query =
                "Select * from " + DBSchema.LoginsTable.NAME +
                        " ;";

        Cursor cursor = mDatabase.rawQuery(Query, null);

        LoginCursorWrapper wrapper = new LoginCursorWrapper(cursor);
        wrapper.moveToLast();

        return wrapper.getLogin();
    }


    public void insertLoggedIn(Login login) {
        ContentValues values = getLoginContentValues(login);
        mDatabase.insert(
                DBSchema.LoginsTable.NAME,
                null,
                values
        );
    }


    public void updateLoggedIn(Login login) {
        String userName = login.getUserName();
        ContentValues values = getLoginContentValues(login);
        mDatabase.update(DBSchema.LoginsTable.NAME,
                values,
                DBSchema.LoginsTable.Cols.USER_NAME + "=?",
                new String[]{userName});
    }

    private static ContentValues getUserContentValues(User user) {
        ContentValues values = new ContentValues();

        values.put(DBSchema.UserTable.Cols.USER_NAME, user.getUserName());
        values.put(DBSchema.UserTable.Cols.EMAIL, user.getEmail());
        values.put(DBSchema.UserTable.Cols.PASSWORD, user.getPassword());
        values.put(DBSchema.UserTable.Cols.FULL_NAME, user.getFullName());
        //values.put(DBSchema.UserTable.Cols.BIRTH_DATE, user.getBirthDate().getTime());
        values.put(DBSchema.UserTable.Cols.HOME_TOWN, user.getHomeTown());
        values.put(DBSchema.UserTable.Cols.PROFILE_PIC, user.getProfilePic());
        values.put(DBSchema.UserTable.Cols.SHORT_BIO, user.getShortBio());

        return values;
    }

    private static ContentValues getFeedItemContentValues(FeedItem feedItem) {
        ContentValues values = new ContentValues();

        values.put(DBSchema.FeedItemsTable.Cols.ID, feedItem.getID().toString());
        values.put(DBSchema.FeedItemsTable.Cols.USER_NAME, feedItem.getUserName());
        values.put(DBSchema.FeedItemsTable.Cols.USER_PIC, feedItem.getUserPic());

        Date rDate = feedItem.getPostedDate();
        values.put(DBSchema.FeedItemsTable.Cols.POSTED_DATE, rDate.getTime());

        values.put(DBSchema.FeedItemsTable.Cols.CONTENT, feedItem.getContent());
        values.put(DBSchema.FeedItemsTable.Cols.PHOTO_PATH, feedItem.getPhotoPath());
        values.put(DBSchema.FeedItemsTable.Cols.STARRED, feedItem.getStarred());

        return values;
    }

    private static ContentValues getFavoriteContentValues(Favorite favorite) {
        ContentValues values = new ContentValues();

        values.put(DBSchema.FavoritesTable.Cols.USER_NAME, favorite.getUserName());
        values.put(DBSchema.FavoritesTable.Cols.FAVORITE, favorite.getFavorite());

        return values;
    }

    private static ContentValues getLoginContentValues(Login login) {
        ContentValues values = new ContentValues();

        values.put(DBSchema.LoginsTable.Cols.USER_NAME, login.getUserName());
        values.put(DBSchema.LoginsTable.Cols.LOGGED_IN, login.getStatus());

        return values;
    }


    public static class UserCursorWrapper extends CursorWrapper{

        public UserCursorWrapper(Cursor cursor) {
            super(cursor);
        }

        public User getUser() {

            User user = new User();
            user.setUserName(getString(getColumnIndex(DBSchema.UserTable.Cols.USER_NAME)));
            user.setEmail(getString(getColumnIndex(DBSchema.UserTable.Cols.EMAIL)));
            user.setPassword(getString(getColumnIndex(DBSchema.UserTable.Cols.PASSWORD)));
            user.setFullName(getString(getColumnIndex(DBSchema.UserTable.Cols.FULL_NAME)));
            user.setBirthDate(new Date(getLong(getColumnIndex(DBSchema.UserTable.Cols.BIRTH_DATE))));
            user.setProfilePic(getString(getColumnIndex(DBSchema.UserTable.Cols.PROFILE_PIC)));
            user.setHomeTown(getString(getColumnIndex(DBSchema.UserTable.Cols.HOME_TOWN)));
            user.setShortBio(getString(getColumnIndex(DBSchema.UserTable.Cols.SHORT_BIO)));

            return user;
        }
    }

    public static class FeedItemCursorWrapper extends CursorWrapper {

        public FeedItemCursorWrapper(Cursor cursor) {
            super(cursor);
        }

        public FeedItem getFeedItem() {

            UUID id = UUID.fromString(getString(getColumnIndex(DBSchema.FeedItemsTable.Cols.ID)));
            FeedItem item = new FeedItem(id);

            item.setUserName(getString(getColumnIndex(DBSchema.FeedItemsTable.Cols.USER_NAME)));
            item.setUserPic(getString(getColumnIndex(DBSchema.FeedItemsTable.Cols.USER_PIC)));
            item.setPostedDate(new Date(getLong(getColumnIndex(DBSchema.FeedItemsTable.Cols.POSTED_DATE))));
            item.setContent(getString(getColumnIndex(DBSchema.FeedItemsTable.Cols.CONTENT)));
            item.setPhotoPath(getString(getColumnIndex(DBSchema.FeedItemsTable.Cols.PHOTO_PATH)));
            item.setStarred(valueOf(getColumnIndex(DBSchema.FeedItemsTable.Cols.STARRED)));

            return item;
        }
    }

    public static class FavoriteCursorWrapper extends CursorWrapper {

        public FavoriteCursorWrapper(Cursor cursor) {
            super(cursor);
        }

        public Favorite getFavorite(String userName) {
            Favorite favorite = new Favorite(userName);

            favorite.setFavorite(getString(getColumnIndex(DBSchema.FavoritesTable.Cols.FAVORITE)));

            return favorite;
        }
    }

    public static class LoginCursorWrapper extends CursorWrapper {

        public LoginCursorWrapper(Cursor cursor) {
            super(cursor);
        }

        public Login getLogin() {

            String username = getString(getColumnIndex(DBSchema.LoginsTable.Cols.USER_NAME));

            boolean status = Boolean.valueOf(getString(getColumnIndex(DBSchema.LoginsTable.Cols.LOGGED_IN)));

            return new Login(username, status);
        }
    }
}