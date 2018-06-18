package br.com.db1.auth.login.result;


/**
 * Created by vinicius.camargo on 07/05/2017.
 */
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
