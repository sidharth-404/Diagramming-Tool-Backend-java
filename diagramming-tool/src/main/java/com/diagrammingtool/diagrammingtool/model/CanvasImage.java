package com.diagrammingtool.diagrammingtool.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;

@Entity
public class CanvasImage {
	

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String imageName;
	@Column(columnDefinition = "TEXT")
	private String imageJson;
	
	@Lob
	@Column(columnDefinition = "BLOB")
	private byte[] imageByte;
	@ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private UserRegistration user;
	
	public CanvasImage() {
		
	}

	public CanvasImage(Long id, String imageName, String imageJson, byte[] imageByte, UserRegistration user) {
		super();
		this.id = id;
		this.imageName = imageName;
		this.imageJson = imageJson;
		this.imageByte = imageByte;
		this.user = user;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public String getImageJson() {
		return imageJson;
	}

	public void setImageJson(String imageJson) {
		this.imageJson = imageJson;
	}

	public byte[] getImageByte() {
		return imageByte;
	}

	public void setImageByte(byte[] imageByte) {
		this.imageByte = imageByte;
	}

	public UserRegistration getUser() {
		return user;
	}

	public void setUser(UserRegistration user) {
		this.user = user;
	}
}
	
	
