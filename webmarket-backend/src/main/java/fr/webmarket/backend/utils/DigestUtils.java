package fr.webmarket.backend.utils;

import fr.webmarket.backend.log.LoggerBundle;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * User: walien
 * Date: 18/04/13
 * Time: 20:45
 */
public class DigestUtils {

    public static String computeMD5(String pwd) {
        try {
            return new String(MessageDigest.getInstance("MD5").digest(pwd.getBytes()));
        } catch (NoSuchAlgorithmException e) {
            LoggerBundle.getDefaultLogger().error(e.getMessage());
        }
        return null;
    }

}
