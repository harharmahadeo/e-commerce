package com.alpidi.model; 

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ListingShopImg{
    @JsonProperty("count") 
    public int getCount() { 
		 return this.count; } 
    public void setCount(int count) { 
		 this.count = count; } 
    int count;
    @JsonProperty("results") 
    public List<ShopImage> getResults() { 
		 return this.results; } 
    public void setResults(List<ShopImage> results) { 
		 this.results = results; } 
    List<ShopImage> results;
}
