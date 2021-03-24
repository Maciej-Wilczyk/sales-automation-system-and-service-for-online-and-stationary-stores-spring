package server.adore_server.response;

public class JwtAuthenticationResponse {
    private String accessToken;
    private String tokenType = "Bearer";
    private long role_id;

    public JwtAuthenticationResponse(String accessToken, long role_id) {
        this.accessToken = accessToken;
        this.role_id = role_id;
    }

    public long getRole_id() {
        return role_id;
    }

    public void setRole_id(long role_id) {
        this.role_id = role_id;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }
}