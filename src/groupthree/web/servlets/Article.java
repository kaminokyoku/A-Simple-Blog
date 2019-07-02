package groupthree.web.servlets;

import org.json.simple.JSONObject;

public class Article {

    private Integer articleId;
    private String title;
    private String date;
    private String content;
    private boolean isShown;
    private Integer userId;

    public Article(Integer articleId, String title, String date, String content, boolean isShown, Integer userId) {
        this.articleId = articleId;
        this.title = title;
        this.date = date;
        this.content = content;
        this.isShown = isShown;
        this.userId = userId;
    }

    public Article(String title, String date, String content, boolean isShown, Integer userId) {
        this.title = title;
        this.date = date;
        this.content = content;
        this.isShown = isShown;
        this.userId = userId;
    }

    public Integer getArticleId() {
        return articleId;
    }

    public void setArticleId(Integer articleId) {
        this.articleId = articleId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isShown() {
        return isShown;
    }

    public boolean getIsShown() {
        return isShown;
    }

    public void setShown(boolean shown) {
        isShown = shown;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public static JSONObject toJson(Article a) {
        JSONObject jObj = new JSONObject();

        if (a != null) {
            jObj.put("articleId", a.getArticleId());
            jObj.put("title", JSONObject.escape(a.getTitle()));
            jObj.put("content", a.getContent());
            jObj.put("date", a.getDate());
            jObj.put("isShow" , a.isShown());
            jObj.put("userId", a.getUserId());
        }

        return jObj;
    }
}
