package com.starfire1337.xenregister;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SocketUtils {

    private String password;

    public SocketUtils(String password) {
        this.password = password;
    }

    public boolean checkAuth(String msg) {
        String[] parts = msg.split("-");
        if(parts.length != 2) {
            return false;
        }
        String check = sha256(parts[1] + this.password);
        return check.equals(parts[0]);
    }

    public String getUser(String msg) {
        String[] parts = msg.split("-");
        if(parts.length == 2) {
            return parts[1];
        }
        return "";
    }

    private String sha256(String str) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            digest.update(str.getBytes());
            byte[] bytes = digest.digest();
            StringBuilder sb = new StringBuilder();
            for (byte b : bytes) {
                sb.append(Integer.toString((b & 0xFF) + 256, 16).substring(1));
            }
            return sb.toString();
        } catch(NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

}
