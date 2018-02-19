package project02.csc214.coucou.Model;

import java.util.Date;
import java.util.UUID;

/**
 * Created by Geraldine
 */

public class FeedItem {
    private UUID mID;
    private String mUserName;
    private String mUserPic;
    private Date mPostedDate;
    private String mContent;
    private String mPhotoPath;
    private int mStarred;

    public FeedItem(UUID id) {
        mID = id;
    }
    public FeedItem() {
        this(UUID.randomUUID());
    }
    public String getUserName() {
        return mUserName;
    }

    public UUID getID() {
        return mID;
    }

    public void setUserName(String userName) {
        mUserName = userName;
    }

    public String getUserPic() {
        return mUserPic;
    }

    public void setUserPic(String userPic) {
        mUserPic = userPic;
    }

    public Date getPostedDate() {
        return mPostedDate;
    }

    public void setPostedDate(Date postedDate) {
        mPostedDate = postedDate;
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String content) {
        mContent = content;
    }

    public String getPhotoPath() {
        return mPhotoPath;
    }

    public void setPhotoPath(String photoPath) {
        mPhotoPath = photoPath;
    }

    public int getStarred() {
        return mStarred;
    }
    public boolean isStarred() {
        if(mStarred != 0)
            return true;
        else
            return false;
    }
    public void addStar() {
        mStarred++;
    }
    public void setStarred(int num) {
        mStarred = num;
    }
}
