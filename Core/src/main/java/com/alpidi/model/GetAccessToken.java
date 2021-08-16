package com.alpidi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GetAccessToken {
    private String grant_type;
    private String client_id;
    private String redirect_uri;
    private String code;
    private String code_verifier;

    public GetAccessToken(String grant_type, String client_id, String redirect_uri, String code,
            String code_verifier) {
        this.grant_type = grant_type;
        this.client_id = client_id;
        this.redirect_uri = redirect_uri;
        this.code = code;
        this.code_verifier = code_verifier;
    }

    public String getGrant_type() {
        return this.grant_type;
    }

    public void setGrant_type(String grant_type) {
        this.grant_type = grant_type;
    }

    public String getClient_id() {
        return this.client_id;
    }

    public void setClient_id(String client_id) {
        this.client_id = client_id;
    }

    public String getRedirect_uri() {
        return this.redirect_uri;
    }

    public void setRedirect_uri(String redirect_uri) {
        this.redirect_uri = redirect_uri;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCode_verifier() {
        return this.code_verifier;
    }

    public void setCode_verifier(String code_verifier) {
        this.code_verifier = code_verifier;
    }

    @Override
    public String toString() {
        return "__GetRefreshToken{" + "grant_type=" + this.grant_type + '\'' + ", client_id='" + this.client_id + '\''
                + ", redirect_uri='" + this.redirect_uri + '\'' + ", code='" + this.code + '\'' + ", code_verifier='"
                + this.code_verifier + '\'' + '}';
    }
}
