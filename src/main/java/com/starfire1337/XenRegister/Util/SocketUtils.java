package com.starfire1337.XenRegister.Util;

import com.starfire1337.XenRegister.XenRegister;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SocketUtils {

    public static boolean checkAuth(String msg) {
        String[] parts = msg.split("-");
        if(parts.length != 2) {
            return false;
        }
        String check = sha256(parts[1] + XenRegister.getInstance().getConfig().getString("password"));
        XenRegister.getInstance().getLogger().info(check);
        XenRegister.getInstance().getLogger().info(parts[0]);
        XenRegister.getInstance().getLogger().info(parts[1]);
        XenRegister.getInstance().getLogger().info(parts[1] + XenRegister.getInstance().getConfig().getString("password"));
        if(check.equals(parts[0])) {
            return true;
        }
        return false;
    }

    public static String getUser(String msg) {
        String[] parts = msg.split("-");
        if(parts.length == 2) {
            return parts[1];
        }
        return "";
    }

    public static String sha256(String str) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            digest.update(str.getBytes());
            byte[] bytes = digest.digest();
            StringBuilder sb = new StringBuilder();
            for(int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xFF) + 256, 16).substring(1));
            }
            return sb.toString();
        } catch(NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

}
