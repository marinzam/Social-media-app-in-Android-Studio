package project02.csc214.coucou.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Geraldine
 */

public class DBOpenHelper extends SQLiteOpenHelper{
    public DBOpenHelper(Context context) {
        super(context, DBSchema.DATABASE_NAME, null, DBSchema.VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + DBSchema.UserTable.NAME
                + "(_id integer primary key autoincrement, "
                + DBSchema.UserTable.Cols.USER_NAME + ", "
                + DBSchema.UserTable.Cols.EMAIL + ", "
                + DBSchema.UserTable.Cols.PASSWORD + ", "
                + DBSchema.UserTable.Cols.FULL_NAME + ", "
                + DBSchema.UserTable.Cols.BIRTH_DATE + ", "
                + DBSchema.UserTable.Cols.HOME_TOWN + ", "
                + DBSchema.UserTable.Cols.PROFILE_PIC + ", "
                + DBSchema.UserTable.Cols.SHORT_BIO + ")"
        );

        db.execSQL("CREATE TABLE " + DBSchema.FeedItemsTable.NAME
                + "(_id integer primary key autoincrement, "
                + DBSchema.FeedItemsTable.Cols.ID + ", "
                + DBSchema.FeedItemsTable.Cols.USER_NAME + ", "
                + DBSchema.FeedItemsTable.Cols.USER_PIC + ", "
                + DBSchema.FeedItemsTable.Cols.POSTED_DATE + ", "
                + DBSchema.FeedItemsTable.Cols.CONTENT + ", "
                + DBSchema.FeedItemsTable.Cols.PHOTO_PATH + ", "
                + DBSchema.FeedItemsTable.Cols.STARRED + ")"
        );

        db.execSQL("CREATE TABLE " + DBSchema.FavoritesTable.NAME
                + "(_id integer primary key autoincrement, "
                + DBSchema.FavoritesTable.Cols.USER_NAME + ", "
                + DBSchema.FavoritesTable.Cols.FAVORITE + ")"
        );

        db.execSQL("CREATE TABLE " + DBSchema.LoginsTable.NAME
                + "(_id integer primary key autoincrement, "
                + DBSchema.LoginsTable.Cols.USER_NAME + ", "
                + DBSchema.LoginsTable.Cols.LOGGED_IN + ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
