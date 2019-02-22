package com.nc.contentsaver.utils;

import io.vertx.core.buffer.Buffer;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The class allows you to search for hash from content data.
 */
public final class Hasher {
    /**
     * Hasher logger.
     */
    private static final Logger LOG = Logger.getLogger(Hasher.class.getSimpleName());
    /**
     * The utility class should not have a public constructor.
     */
    private Hasher() {
    }

    /**
     * Returns the SHA-256 data cache.
     *
     * @param data data to get hash
     * @return sha-256 hash
     */
    public static String getSha256(Buffer data) {
        try {
            byte[] hash = MessageDigest.getInstance("SHA-256").digest(data.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte aHash : hash) {
                final int k = 0xff;
                String hex = Integer.toHexString(k & aHash);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            //it will never happen
            LOG.log(Level.SEVERE, "Sha-256 algorithm is not found", e);
            return "";
        }
    }
}
