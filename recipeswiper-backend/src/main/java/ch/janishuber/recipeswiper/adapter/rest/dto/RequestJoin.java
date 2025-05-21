package ch.janishuber.recipeswiper.adapter.rest.dto;

public class RequestJoin {
    private String groupToken;
    private String userToken;

    public RequestJoin() {
    }

    public RequestJoin(String groupToken, String userToken) {
        this.groupToken = groupToken;
        this.userToken = userToken;
    }

    public String getGroupToken() {
        return groupToken;
    }
    public void setGroupToken(String groupToken) {
        this.groupToken = groupToken;
    }
    public String getUserToken() {
        return userToken;
    }
    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }
}
