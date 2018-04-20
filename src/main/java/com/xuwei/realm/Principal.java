package com.xuwei.realm;

import java.io.Serializable;

/**
 * @author csj
 * @Description: 会员登录存放在session的信息
 * @Copyright: 福州骏华信息有限公司 (c)2015
 * @Created Date : 2015-3-5
 * @vesion 1.0
 */
public class Principal implements Serializable {
    private static final long serialVersionUID = 5798882004228239559L;
    private Long id;
    private String username;
    private String name;

    public Principal(Long id, String username,String name) {
        this.id = id;
        this.username = username;
        this.name = name;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String toString() {
        return this.username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
