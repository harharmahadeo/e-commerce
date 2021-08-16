package com.alpidi.payload.request;

import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class SignupRequest {
  @NotBlank
  @Size(min = 3, max = 20)
  private String username;

  @NotBlank
  @Size(max = 20)
  private String firstname;

  @NotBlank
  @Size(max = 20)
  private String lastname;

  @NotBlank
  @Size(max = 50)
  @Email
  private String email;
  
  @NotBlank
  @Size(max = 20)
  private String role;

  @NotBlank
  @Size(max = 20)
  private String photo;

  @NotBlank
  @Size(max = 20)
  private String provider;

  @NotBlank
  @Size(max = 20)
  private String authtoken;

  @NotBlank
  @Size(max = 20)
  private String socialid;

  @NotBlank
  @Size(min = 6, max = 40)
  private String password;
  
  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getFirstname() {
   return firstname;
   }

   public void setFirstname(String firstname) {
     this.firstname = firstname;
   }

   public String getLastname() {
     return lastname;
   }

   public void setLastname(String lastname) {
     this.lastname = lastname;
   }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getRole() {
	    return role;
	}

	public void setRole(String role) {
	    this.role = role;
	}

  public String getPhoto() {
   return photo;
  }

  public void setPhoto(String photo) {
   this.photo = photo;
  }

  public String getProvider() {
    return provider;
  }

  public void setProvider(String provider) {
    this.provider = provider;
  }
  public String getAuthtoken() {
    return authtoken;
  }

  public void setAuthtoken(String authtoken) {
    this.authtoken = authtoken;
  }
  public String getSocialid() {
    return socialid;
  }

  public void setSocialid(String socialid) {
    this.socialid = socialid;
  }
}