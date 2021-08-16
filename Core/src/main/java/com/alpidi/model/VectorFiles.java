package com.alpidi.model;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "vectorfiles")
public class VectorFiles {
	@Id
	private String id;

	@NotBlank
	@Size(max = 20)
	private String shopid;
	
	@NotBlank
	@Size(max = 20)
	private String listingid;
	
	@NotBlank
	@Size(max = 20)
	private List<String> filename;
	
	public VectorFiles(String shopid,String listingid,List<String> filename)
	{
		this.shopid=shopid;
		this.listingid=listingid;
		this.filename=filename;
	}
	
	public String getShopid() {
		return shopid;
	}

	public void setShopid(String shopid) {
		this.shopid = shopid;
	}
	public String getlistingid() {
		return shopid;
	}

	public void setlistingid(String listingid) {
		this.listingid = listingid;
	}
	public List<String> getfilename() {
		return filename;
	}

	public void setfilename(List<String> filename) {
		this.filename = filename;
	}
}
