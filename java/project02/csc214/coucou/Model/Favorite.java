package project02.csc214.coucou.Model;

/**
 * Created by Geraldine
 */

public class Favorite {
    private String mUserName;
    private String mFavorited;

    public Favorite(String userName) {
        mUserName = userName;
    }

    public String getUserName() {
        return mUserName;
    }

    public void setFavorite(String favorite) {
        mFavorited = favorite;
    }
    public String getFavorite() {
        return mFavorited;
    }
}
