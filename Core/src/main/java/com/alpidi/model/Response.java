package com.alpidi.model;

import org.springframework.lang.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Response {
	private Integer code;
	private String message;
	@Nullable
	private String authCode;
	@Nullable
	private EtsyTokenDetails tokenDetail;
	@Nullable
	private _Error errorDetail;
	@Nullable
	private String codeChallenge;
	@Nullable
	private String codeVerifier;
	@Nullable
	private String[] result;

	public Response(Integer code, String message, String authCode, EtsyTokenDetails tokenDetail, _Error errorDetail,
		String codeChallenge, String codeVerifier,String[] result) {
		this.code = code;
		this.message = message;
		this.authCode = authCode;
		this.tokenDetail = tokenDetail;
		this.errorDetail = errorDetail;
		this.codeChallenge = codeChallenge;
		this.codeVerifier = codeVerifier;
		this.result = result;
	}

	public Integer getCode() {
		return this.code;
	}

	public String getMessage() {
		return this.message;
	}

	public String getAuthCode() {
		return this.authCode;
	}

	public EtsyTokenDetails getTokenDetail() {
		return this.tokenDetail;
	}

	public _Error getErrorDetail() {
		return this.errorDetail;
	}

	public String getCodeChallenge() {
		return this.codeChallenge;
	}

	public String getCodeVerifier() {
		return this.codeVerifier;
	}
	
	public String[] getResult() {
		return this.result;
	}

	public void setCode(Integer code) {
		this.code = code;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setAuthCode(@Nullable String authCode) {
		if (authCode != null && authCode.isEmpty()) {
			authCode = null;
		}
		this.authCode = authCode;
	}

	public void setTokenDetail(@Nullable EtsyTokenDetails tokenDetail) {
		this.tokenDetail = tokenDetail;
	}

	public void setErrorDetail(@Nullable _Error errorDetail) {
		this.errorDetail = errorDetail;
	}

	public void setCodeChallenge(@Nullable String codeChallenge) {
		if (codeChallenge != null && codeChallenge.isEmpty()) {
			codeChallenge = null;
		}
		this.codeChallenge = codeChallenge;
	}

	public void setCodeVerifier(@Nullable String codeVerifier) {
		if (codeVerifier != null && codeVerifier.isEmpty()) {
			codeVerifier = null;
		}
		this.codeVerifier = codeVerifier;
	}

	public void setResult(@Nullable String[] result) {
		this.result = result;
	}
	
	@Override
	public String toString() {
		return "Response{" + "code=" + this.code + '\'' + ", message='" + this.message + '\'' + ", auth_code='"
		+ this.authCode + '\'' + ", token_detail=" + tokenDetail + ", error_detail=" + errorDetail
		+ ", code_challenge='" + this.codeChallenge + '\'' + ", code_verifier='" + this.codeVerifier + '\''
		+ '}';
	}
}
