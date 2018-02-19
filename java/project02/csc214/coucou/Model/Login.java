package project02.csc214.coucou.Model;

/**
 * Created by Geraldine
 */

public class Login {
    private String mUserName;
    private boolean mStatus;

    public Login(String userName, boolean status) {
        mUserName = userName;
        mStatus = status;
    }

    public String getUserName() {
        return mUserName;
    }

    public boolean getStatus() {
        return mStatus;
    }
}
