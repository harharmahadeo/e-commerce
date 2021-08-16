package com.alpidi.model; 
import com.fasterxml.jackson.annotation.JsonProperty; 
import java.util.List; 
public class Listing{
    @JsonProperty("count") 
    public int getCount() { 
		 return this.count; } 
    public void setCount(int count) { 
		 this.count = count; } 
    int count;
    @JsonProperty("results") 
    public List<ListingResult> getResults() { 
		 return this.results; } 
    public void setResults(List<ListingResult> results) { 
		 this.results = results; } 
    List<ListingResult> results;
}
