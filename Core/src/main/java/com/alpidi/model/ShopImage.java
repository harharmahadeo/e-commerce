package com.alpidi.model; 

import com.fasterxml.jackson.annotation.JsonProperty;

public class ShopImage{
    @JsonProperty("listing_id") 
    public int getListing_id() { 
		 return this.listing_id; } 
    public void setListing_id(int listing_id) { 
		 this.listing_id = listing_id; } 
    int listing_id;
    @JsonProperty("listing_image_id") 
    public Object getListing_image_id() { 
		 return this.listing_image_id; } 
    public void setListing_image_id(Object listing_image_id) { 
		 this.listing_image_id = listing_image_id; } 
    Object listing_image_id;
    @JsonProperty("hex_code") 
    public String getHex_code() { 
		 return this.hex_code; } 
    public void setHex_code(String hex_code) { 
		 this.hex_code = hex_code; } 
    String hex_code;
    @JsonProperty("red") 
    public int getRed() { 
		 return this.red; } 
    public void setRed(int red) { 
		 this.red = red; } 
    int red;
    @JsonProperty("green") 
    public int getGreen() { 
		 return this.green; } 
    public void setGreen(int green) { 
		 this.green = green; } 
    int green;
    @JsonProperty("blue") 
    public int getBlue() { 
		 return this.blue; } 
    public void setBlue(int blue) { 
		 this.blue = blue; } 
    int blue;
    @JsonProperty("hue") 
    public int getHue() { 
		 return this.hue; } 
    public void setHue(int hue) { 
		 this.hue = hue; } 
    int hue;
    @JsonProperty("saturation") 
    public int getSaturation() { 
		 return this.saturation; } 
    public void setSaturation(int saturation) { 
		 this.saturation = saturation; } 
    int saturation;
    @JsonProperty("brightness") 
    public int getBrightness() { 
		 return this.brightness; } 
    public void setBrightness(int brightness) { 
		 this.brightness = brightness; } 
    int brightness;
    @JsonProperty("is_black_and_white") 
    public boolean getIs_black_and_white() { 
		 return this.is_black_and_white; } 
    public void setIs_black_and_white(boolean is_black_and_white) { 
		 this.is_black_and_white = is_black_and_white; } 
    boolean is_black_and_white;
    @JsonProperty("creation_tsz") 
    public int getCreation_tsz() { 
		 return this.creation_tsz; } 
    public void setCreation_tsz(int creation_tsz) { 
		 this.creation_tsz = creation_tsz; } 
    int creation_tsz;
    @JsonProperty("rank") 
    public int getRank() { 
		 return this.rank; } 
    public void setRank(int rank) { 
		 this.rank = rank; } 
    int rank;
    @JsonProperty("url_75x75") 
    public String getUrl_75x75() { 
		 return this.url_75x75; } 
    public void setUrl_75x75(String url_75x75) { 
		 this.url_75x75 = url_75x75; } 
    String url_75x75;
    @JsonProperty("url_170x135") 
    public String getUrl_170x135() { 
		 return this.url_170x135; } 
    public void setUrl_170x135(String url_170x135) { 
		 this.url_170x135 = url_170x135; } 
    String url_170x135;
    @JsonProperty("url_570xN") 
    public String getUrl_570xN() { 
		 return this.url_570xN; } 
    public void setUrl_570xN(String url_570xN) { 
		 this.url_570xN = url_570xN; } 
    String url_570xN;
    @JsonProperty("url_fullxfull") 
    public String getUrl_fullxfull() { 
		 return this.url_fullxfull; } 
    public void setUrl_fullxfull(String url_fullxfull) { 
		 this.url_fullxfull = url_fullxfull; } 
    String url_fullxfull;
    @JsonProperty("full_height") 
    public int getFull_height() { 
		 return this.full_height; } 
    public void setFull_height(int full_height) { 
		 this.full_height = full_height; } 
    int full_height;
    @JsonProperty("full_width") 
    public int getFull_width() { 
		 return this.full_width; } 
    public void setFull_width(int full_width) { 
		 this.full_width = full_width; } 
    int full_width;
}
