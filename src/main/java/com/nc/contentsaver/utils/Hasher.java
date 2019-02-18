package com.nc.contentsaver.utils;

import io.vertx.core.buffer.Buffer;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Hasher {
    private Hasher() {
    }

    public static String getSha256(Buffer data) {
        try {
            byte[] hash = MessageDigest.getInstance("SHA-256").digest(data.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte aHash : hash) {
                String hex = Integer.toHexString(0xff & aHash);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            //it will never happen
            e.printStackTrace();
            return "";
        }
    }
}
