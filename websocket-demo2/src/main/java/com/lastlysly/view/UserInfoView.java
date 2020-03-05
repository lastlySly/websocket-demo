package com.lastlysly.view;

import java.util.Set;

/**
 * @author lastlySly
 * @GitHub https://github.com/lastlySly
 * @create 2020-03-04 16:00
 * 登录的用户信息和权限信息
 **/
public class UserInfoView {
    /**
     * token
     */
    private String token;
    /**
     * 用户ID
     */
    private String userId;
    /**
     * 用户姓名
     */
    private String userName;
    /**
     * 角色ID
     */
    private String roleId;
    /**
     * 有权限的菜单和功能按钮
     */
    private Set<String> menus;
    /**
     * 有权限访问的企业ID列表
     */
    private Set<String> privilegeOwnerIds;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public Set<String> getMenus() {
        return menus;
    }

    public void setMenus(Set<String> menus) {
        this.menus = menus;
    }

    public Set<String> getPrivilegeOwnerIds() {
        return privilegeOwnerIds;
    }

    public void setPrivilegeOwnerIds(Set<String> privilegeOwnerIds) {
        this.privilegeOwnerIds = privilegeOwnerIds;
    }
}
