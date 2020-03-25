package com.chanfinecloud.cfl.entity;

import java.io.Serializable;

/**
 * Created by Loong on 2020/2/7.
 * Version: 2.0
 * Describe: Token缓存实体
 * Update: 实体字段改变，过期字段将不能使用
 */
public class TokenEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    @Deprecated
    private String token;
    @Deprecated
    private long expireTime;
    @Deprecated
    public TokenEntity(String token, long expireTime) {
        this.token = token;
        this.expireTime = expireTime;
    }
    @Deprecated
    public String getToken() {
        return token;
    }
    @Deprecated
    public void setToken(String token) {
        this.token = token;
    }
    @Deprecated
    public long getExpireTime() {
        return expireTime;
    }
    @Deprecated
    public void setExpireTime(long expireTime) {
        this.expireTime = expireTime;
    }

    /**
     * access_token : d07ef832-137e-410a-b75c-74dc7f4b1de7
     * token_type : bearer
     * refresh_token : 84825608-149b-4611-959f-49b046b92e9f
     * expires_in : 179403
     * scope : all
     */

    private String access_token;
    private String token_type;
    private String refresh_token;
    private int expires_in;
    private String scope;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getToken_type() {
        return token_type;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }

    public String getRefresh_token() {
        return refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    public int getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(int expires_in) {
        this.expires_in = expires_in;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    private long init_time;

    public long getInit_time() {
        return init_time;
    }

    public void setInit_time(long init_time) {
        this.init_time = init_time;
    }

    @Override
    public String toString() {
        return "TokenEntity{" +
                "token='" + token + '\'' +
                ", expireTime=" + expireTime +
                ", access_token='" + access_token + '\'' +
                ", token_type='" + token_type + '\'' +
                ", refresh_token='" + refresh_token + '\'' +
                ", expires_in=" + expires_in +
                ", scope='" + scope + '\'' +
                ", init_time=" + init_time +
                '}';
    }
}
