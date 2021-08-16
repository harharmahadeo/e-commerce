package com.alpidi.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "shops")
public class Shops {
    @Id
    private String id;

    @NotBlank
    @Size(max = 20)
    private String shop_id;

    @NotBlank
    @Size(max = 20)
    private String shop_name;
    
    @NotBlank
    @Size(max = 20)
    private String user_id;

    @NotBlank
    @Size(max = 20)
    private String etsyuserid;

    @NotBlank
    @Size(max = 20)
    private String currency_code;

    @NotBlank
    @Size(max = 50)
    private String listing_active_count;
    
    @NotBlank
    @Size(max = 50)
    private String login_name;
    
    @NotBlank
    @Size(max = 50)
    private String accepts_custom_requests;
    
    @NotBlank
    @Size(max = 50)
    private String url;
    
    @Size(max = 50)
    private String image_url;

    @NotBlank
    @Size(max = 50)
    private String num_favorers;
    
    @Size(max = 50)
    private String policy_welcome;
    
    @Size(max = 50)
    private String policy_payment;
    
    @Size(max = 50)
    private String policy_shipping;
    
    @Size(max = 50)
    private String policy_refunds;
    
    @Size(max = 50)
    private String policy_additional;
    
    @Size(max = 50)
    private String policy_seller_info;
    
    @NotBlank
    @Size(max = 50)
    private String policy_update_date;

    @NotBlank
    @Size(max = 50)
    private String policy_has_private_receipt_info;

    public Shops(String shop_id,String shop_name,String user_id,String etsyuserid,String currency_code,String listing_active_count,String login_name,String accepts_custom_requests,String url,String image_url,String num_favorers,String policy_welcome,String policy_payment,String policy_shipping,String policy_refunds,String policy_additional,String policy_seller_info,String policy_update_date,String policy_has_private_receipt_info) {
    	
    	this.shop_id = shop_id;
        this.shop_name = shop_name;
        
        if(user_id==null)
        {
        	this.user_id = "";
        }
        else
        {
        	this.user_id = user_id;
        }
        this.etsyuserid = etsyuserid;
        this.currency_code = currency_code;
        this.listing_active_count=listing_active_count;
        this.login_name=login_name;
        this.accepts_custom_requests=accepts_custom_requests;
        this.url=url;
        if(image_url==null)
        {
        	this.image_url="";
        }
        else
        {
        	this.image_url=image_url;
        }
        
        this.num_favorers=num_favorers;
        if(policy_welcome==null)
        {
        	this.policy_welcome="";
        }
        else
        {
        	this.policy_welcome=policy_welcome;
        }
        if(policy_payment==null)
        {
        	this.policy_payment="";
        }else {
        	this.policy_payment=policy_payment;
        }
        if(policy_shipping==null)
        {
        	this.policy_shipping="";
        }else {
        	this.policy_shipping=policy_shipping;
        }
        if(policy_refunds==null)
        {
        	this.policy_refunds="";
        }else {
        	this.policy_refunds=policy_refunds;
        }
        if(policy_additional==null)
        {
        	this.policy_additional="";
        }else {
        	this.policy_additional=policy_additional;
        }
        if(policy_seller_info==null)
        {
        	this.policy_seller_info="";
        }else {
        	this.policy_seller_info=policy_seller_info;
        }
        this.policy_update_date=policy_update_date;
        this.policy_has_private_receipt_info=policy_has_private_receipt_info;
    }
   
    public String getshopid() {
        return shop_id;
    }
    public String getshopname() {
        return shop_name;
    }
    public String getuser_id() {
        return user_id;
    }
    public String getetsyuserid() {
        return etsyuserid;
    }
    public String getcurrencycode() {
        return currency_code;
    }
    public String getlogin_name() {
        return login_name;
    }
    public String getlisting_active_count() {
        return listing_active_count;
    }
    public String getaccepts_custom_requests() {
        return accepts_custom_requests;
    }
    public String geturl() {
        return url;
    }
    public String getimage_url() {
        return image_url;
    }
    public String getnum_favorers() {
        return num_favorers;
    }
    public String getpolicy_welcome() {
        return policy_welcome;
    }
    public String getpolicy_payment() {
        return policy_payment;
    }
    public String getpolicy_shipping() {
        return policy_shipping;
    }
    public String getpolicy_refunds() {
        return policy_refunds;
    }
    public String getpolicy_additional() {
        return policy_additional;
    }
    public String getpolicy_seller_info() {
        return policy_seller_info;
    }
    public String getpolicy_update_date() {
        return policy_update_date;
    }
    public String getpolicy_has_private_receipt_info() {
        return policy_has_private_receipt_info;
    }

    public void setshopid(String shop_id) {
        this.shop_id = shop_id;
    }
    public void setshopname(String shop_name) {
        this.shop_name = shop_name;
    }
    public void setetsyuserid(String etsyuserid) {
        this.etsyuserid = etsyuserid;
    }
    public void setcurrency_code(String currency_code) {
        this.currency_code = currency_code;
    }
    public void setlogin_name(String login_name) {
        this.login_name = login_name;
    }
    public void setlisting_active_count(String listing_active_count) {
        this.listing_active_count = listing_active_count;
    }
    public void setaccepts_custom_requests(String accepts_custom_requests) {
        this.accepts_custom_requests = accepts_custom_requests;
    }
    public void seturl(String url) {
        this.url = url;
    }
    public void setimage_url(String image_url) {
        this.image_url = image_url;
    }
    public void setnum_favorers(String num_favorers) {
        this.num_favorers = num_favorers;
    }
    public void setpolicy_welcome(String policy_welcome) {
        this.policy_welcome = policy_welcome;
    }
    public void setpolicy_payment(String policy_payment) {
        this.policy_payment = policy_payment;
    }
    public void setpolicy_shipping(String policy_shipping) {
        this.policy_shipping = policy_shipping;
    }
    public void setpolicy_refunds(String policy_refunds) {
        this.policy_refunds = policy_refunds;
    }
    public void setpolicy_additional(String policy_additional) {
        this.policy_additional = policy_additional;
    }
    public void setpolicy_seller_info(String policy_seller_info) {
        this.policy_seller_info = policy_seller_info;
    }
    public void setpolicy_update_date(String policy_update_date) {
        this.policy_update_date = policy_update_date;
    }
    public void setpolicy_has_private_receipt_info(String policy_has_private_receipt_info) {
        this.policy_has_private_receipt_info = policy_has_private_receipt_info;
    }
}
