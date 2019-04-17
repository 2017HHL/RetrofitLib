package net.huansi.retrofit.bean;

/**
 * Created by Administrator on 2019/3/27 0027.
 * token请求参数
 */

public class ParamTokenBean extends ResultBaseBean {
    private String userName;
    private String passWord;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }
}
