package com.alpidi.model; 
import com.fasterxml.jackson.annotation.JsonProperty;

public class ListingPrice{
    @JsonProperty("amount") 
    public int getAmount() { 
		 return this.amount; } 
    public void setAmount(int amount) { 
		 this.amount = amount; } 
    int amount;
    @JsonProperty("divisor") 
    public int getDivisor() { 
		 return this.divisor; } 
    public void setDivisor(int divisor) { 
		 this.divisor = divisor; } 
    int divisor;
    @JsonProperty("currency_code") 
    public String getCurrency_code() { 
		 return this.currency_code; } 
    public void setCurrency_code(String currency_code) { 
		 this.currency_code = currency_code; } 
    String currency_code;
}
