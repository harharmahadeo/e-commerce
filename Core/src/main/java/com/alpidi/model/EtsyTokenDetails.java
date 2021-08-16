package com.alpidi.model;

import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
@Document(collection = "etsyauthorization")
public class EtsyTokenDetails {

    private String access_token;
    private String token_type;
    private Integer expires_in;
    private String refresh_token;

    public EtsyTokenDetails(String access_token, String token_type, Integer expires_in, String refresh_token) {
        this.access_token = access_token;
        this.token_type = token_type;
        this.expires_in = expires_in;
        this.refresh_token = refresh_token;
    }

    public String getAccess_token() {
        return this.access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getToken_type() {
        return this.token_type;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }

    public Integer getExpires_in() {
        return this.expires_in;
    }

    public void setExpires_in(Integer expires_in) {
        this.expires_in = expires_in;
    }

    public String getRefresh_token() {
        return this.refresh_token;
    }

    public void setRefresh_token(String refresh_token) {
        this.refresh_token = refresh_token;
    }

    @Override
    public String toString() {
        return "_GetRefreshToken{" + "access_token=" + this.access_token + '\'' + ", token_type='" + this.token_type
                + '\'' + ", expires_in='" + this.expires_in + ", refresh_token='" + this.refresh_token + '\'' + '}';
    }
}
