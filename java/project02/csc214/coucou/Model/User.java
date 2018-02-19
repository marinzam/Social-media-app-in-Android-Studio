package project02.csc214.coucou.Model;

import java.util.Date;

/**
 * Created by Geraldine
 */

public class User {
    private String mUserName;
    private String mEmail;
    private String mPassword;
    private String mFullName;
    private Date mBirthDate;
    private String mHomeTown;
    private String mProfilePic;
    private String mShortBio;

    public User() {

    }

    public String getUserName() {
        return mUserName;
    }

    public void setUserName(String userName) {
        mUserName = userName;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        mPassword = password;
    }

    public String getFullName() {
        return mFullName;
    }

    public void setFullName(String fullName) {
        mFullName = fullName;
    }

    public Date getBirthDate() {
        return mBirthDate;
    }

    public void setBirthDate(Date birthDate) {
        mBirthDate = birthDate;
    }

    public String getHomeTown() {
        return mHomeTown;
    }

    public void setHomeTown(String homeTown) {
        mHomeTown = homeTown;
    }

    public String getProfilePic() {
        return mProfilePic;
    }

    public void setProfilePic(String profilePic) {
        mProfilePic = profilePic;
    }

    public String getShortBio() {
        return mShortBio;
    }

    public void setShortBio(String shortBio) {
        mShortBio = shortBio;
    }
}
