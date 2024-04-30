package com.diagrammingtool.diagrammingtool.model;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

@Entity
public class UserRegistration implements UserDetails   {

	  @Id
	  @GeneratedValue(strategy = GenerationType.IDENTITY)
	  private Long userId;
	  
	  @NotEmpty(message = "First name is required")
	  @Size(min = 2, max = 20, message = "First name must be between 2 and 20 characters")
	  private String firstName;
	  
	  private String lastName;
	  
	  @NotEmpty(message = "Email is required")
	  @Email(message = "Invalid email format")
	  private String userEmail;

	  @NotEmpty(message = "Password is required")
	  private String password;
	 
	    @CreationTimestamp
	    @Column(updatable = false, name = "created_at",columnDefinition = "datetime")
	    private Date createdAt;

	    @UpdateTimestamp
	    @Column(name = "updated_at",columnDefinition = "datetime")
	    private Date updatedAt;
	  
	  public UserRegistration()
	  {
	  }
	  
	  public UserRegistration(String firstName, String lastName, String userEmail, String password) {
			super();
			this.firstName = firstName;
			this.lastName = lastName;
			this.userEmail = userEmail;
			this.password = password;
		}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	


	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return List.of();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return userEmail;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
	  
	  

	

}
