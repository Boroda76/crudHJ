package util;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * USED IN HEADERS BEFORE I KNEW ABOUT SESSIONS IN SERVLETS...rukalitso
 */
public class MD5Hasher {
    public static String md5(String input) {

        String md5 = null;
        input+="B *N%&V86B%pb57*5-";//sort of salt =)
        if(null == input) return null;

        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            digest.update(input.getBytes(), 0, input.length());
            md5 = new BigInteger(1, digest.digest()).toString(16);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return md5;
    }
}
