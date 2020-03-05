package com.lastlysly.websokcet;

import com.lastlysly.view.UserInfoView;

/**
 * @author lastlySly
 * @GitHub https://github.com/lastlySly
 * @create 2020-03-04 16:09
 **/
public class ConnectorSessionContext {
    /**
     * 前端连接的登录用户信息
     */
    private UserInfoView user;

    public UserInfoView getUser() {
        return user;
    }

    public void setUser(UserInfoView user) {
        this.user = user;
    }
    public String getUserId() {
        return user != null ? user.getUserId() : null;
    }
    public String getUserName() {
        return user != null ? user.getUserName() : null;
    }
}
