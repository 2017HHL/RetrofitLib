package net.huansi.retrofit.bean;

/**
 * 服务器返回的token
 */
public class ResultTokenBean extends ResultBaseBean{
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
