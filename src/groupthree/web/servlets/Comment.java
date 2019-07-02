package groupthree.web.servlets;

import org.json.simple.JSONObject;

import java.sql.Timestamp;

public class Comment {
    private int id;
    private int parentID;
    private String content;
    private Timestamp date;
    private int articleId;
    private int userId;
    private boolean isShown;
    private String userName;
    private String userAvatar;

    public Comment(int id, int parentID, String content, Timestamp date, int articleId, int userId, boolean isShown) {
        this.id = id;
        this.parentID = parentID;
        this.content = content;
        this.date = date;
        this.articleId = articleId;
        this.userId = userId;
        this.isShown = isShown;
    }

    public Comment(int parentID, String content, Timestamp date, int articleId, int userId, boolean isShown) {
        this.parentID = parentID;
        this.content = content;
        this.date = date;
        this.articleId = articleId;
        this.userId = userId;
        this.isShown = isShown;
    }

    public Comment(int id, int parentID, String content, int articleId, int userId, boolean isShown) {
        this.id = id;
        this.parentID = parentID;
        this.content = content;
        this.articleId = articleId;
        this.userId = userId;
        this.isShown = isShown;
    }

    public Comment(int parentID, String content, int articleId, int userId, boolean isShown) {
        this.parentID = parentID;
        this.content = content;
        this.articleId = articleId;
        this.userId = userId;
        this.isShown = isShown;
    }

    public Comment(String content, Timestamp date, int articleId, int userId, boolean isShown) {
        this.content = content;
        this.date = date;
        this.articleId = articleId;
        this.userId = userId;
        this.isShown = isShown;
    }

    public Comment(int parentID, String content, Timestamp date, int articleId, int userId, boolean isShown, String userName, String userAvatar) {
        this.parentID = parentID;
        this.content = content;
        this.date = date;
        this.articleId = articleId;
        this.userId = userId;
        this.isShown = isShown;
        this.userName = userName;
        this.userAvatar = userAvatar;
    }

    public Comment(int id, int parentID, String content, Timestamp date, int articleId, int userId, boolean isShown, String userName, String userAvatar) {
        this.id = id;
        this.parentID = parentID;
        this.content = content;
        this.date = date;
        this.articleId = articleId;
        this.userId = userId;
        this.isShown = isShown;
        this.userName = userName;
        this.userAvatar = userAvatar;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getParentID() {
        return parentID;
    }

    public void setParentID(int parentID) {
        this.parentID = parentID;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getArticleId() {
        return articleId;
    }

    public void setArticleId(int articleId) {
        this.articleId = articleId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public boolean isShown() {
        return isShown;
    }

    public void setShown(boolean shown) {
        isShown = shown;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    public static JSONObject toJson(Comment c) {
        JSONObject jObj = new JSONObject();

        if (c != null) {
            jObj.put("id", c.getId());
            jObj.put("parentID", c.getParentID());
            jObj.put("content", c.getContent());
            jObj.put("date", JSONObject.escape(c.getDate().toString()));
            jObj.put("articleId" , c.getArticleId());
            jObj.put("userId", c.getUserId());
            jObj.put("isShow" , c.isShown());
            jObj.put("userName", c.getUserName());
            jObj.put("userAvatar", c.getUserAvatar());
        }

        return jObj;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }
}
