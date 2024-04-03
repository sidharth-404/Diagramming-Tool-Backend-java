package com.diagrammingtool.app.model;

import javax.persistence.*;

@Entity
@Table(name = "canvas_images")
public class CanvasImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(name = "image_data", nullable = false)
    private byte[] imageData;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private UserRegistration user;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public byte[] getImageData() {
		return imageData;
	}

	public void setImageData(byte[] imageData) {
		this.imageData = imageData;
	}

	public UserRegistration getUser() {
		return user;
	}

	public void setUser(UserRegistration user) {
		this.user = user;
	}

	public CanvasImage(Long id, byte[] imageData, UserRegistration user) {
		super();
		this.id = id;
		this.imageData = imageData;
		this.user = user;
	}

	public CanvasImage() {
		super();
	}

    
}
