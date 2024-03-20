package com.diagrammingtool.app.service;
import org.mindrot.jbcrypt.BCrypt;
public class PasswordEncryption {
	public String hashPassword(String plainTextPassword) {
        return BCrypt.hashpw(plainTextPassword, BCrypt.gensalt());
    }

    public boolean verifyPassword(String plainTextPassword, String hashedPassword) {
        return BCrypt.checkpw(plainTextPassword, hashedPassword);
    }

}
