package com.diagrammingtool.app.dto;


public class ResetPasswordRequest {

    private String userEmail;
    private String newPassword;
    private String otp;

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }




	public ResetPasswordRequest() {
	
	}

	
}
