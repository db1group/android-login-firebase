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
<<<<<<< HEAD

=======
>>>>>>> e2ce8576fbb91026a89a6d1015399299b0853e5e
    @Override
    public String toString() {
        return "FacebookResult{" +
                "token='" + token + '\'' +
                ", userId='" + userId + '\'' +
                ", applicationId='" + applicationId + '\'' +
                '}';
    }
<<<<<<< HEAD
=======

>>>>>>> e2ce8576fbb91026a89a6d1015399299b0853e5e
}
