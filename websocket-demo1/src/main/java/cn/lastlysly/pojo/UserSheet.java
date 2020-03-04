package cn.lastlysly.pojo;

/**
 * @author lastlySly
 * @GitHub https://github.com/lastlySly
 * @create 2018-05-28 17:21
 **/
public class UserSheet {

    private String username;
    private String pwd;



    public UserSheet() {}

    public UserSheet(String username, String pwd) {
        super();
        this.username = username;
        this.pwd = pwd;
    }


    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPwd() {
        return pwd;
    }
    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

}
