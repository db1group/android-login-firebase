package br.com.db1.auth.login.result;

public class FacebookResult {
    public String token;
    public String userId;
    public String applicationId;

    public FacebookResult(String token, String userId, String applicationId) {
        this.token = token;
        this.userId = userId;
        this.applicationId = applicationId;
    }
    @Override
    public String toString() {
        return "FacebookResult{" +
                "token='" + token + '\'' +
                ", userId='" + userId + '\'' +
                ", applicationId='" + applicationId + '\'' +
                '}';
    }
}
