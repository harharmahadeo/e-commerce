package com.alpidi.model;

import java.util.Date;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "etsyauthorization")
public class Etsy {
	@Id
	private String id;

	@NotBlank
	@Size(max = 20)
	private String access_token;

	@NotBlank
	@Size(max = 20)
	private String token_type;

	@NotBlank
	@Size(max = 20)
	private String refresh_token;

	@NotBlank
	@Size(max = 20)
	private Date expire_in;

	@NotBlank
	@Size(max = 50)
	private String userid;
	
	@NotBlank
	@Size(max = 50)
	private String etsyuserid;


	public Etsy(String access_token,String token_type,String refresh_token, Date expire_in,String userid,String etsyuserid) {
		this.access_token = access_token;
		this.token_type = token_type;
		this.refresh_token = refresh_token;
		this.expire_in = expire_in;
		this.userid = userid;
		this.etsyuserid=etsyuserid;
	}



	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAccesstoken() {
		return access_token;
	}

	public void setAccesstoken(String access_token) {
		this.access_token = access_token;
	}

	public String getTokentype() {
		return token_type;
	}

	public void setTokentyoe(String token_type) {
		this.token_type = token_type;
	}

	public String getRefreshtoken() {
		return refresh_token;
	}

	public void setRefreshtoken(String refresh_token) {
		this.refresh_token = refresh_token;
	}

	public Date getExpirein() {
		return expire_in;
	}

	public void setExpirein(Date expire_in) {
		this.expire_in = expire_in;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getEtsyuserid() {
		return etsyuserid;
	}

	public void setEtsyuserid(String etsyuserid) {
		this.etsyuserid = etsyuserid;
	}
}

