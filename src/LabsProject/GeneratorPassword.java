package LabsProject;

import java.security.MessageDigest;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class GeneratorPassword {

    public static final int length = 8;

    public static final String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public static final String lower = upper.toLowerCase(Locale.ROOT);

    public static final String digits = "0123456789";
    public static final String spes =  "!\"#$%&\'()*+,-./:;<=>?@[\\]^_`{|}~(";

    public static final String alphanum = upper + lower + digits + spes;

    private static final String pepper = "*&^mVLC(#";

    public static String get() {
        char[] chars = alphanum.toCharArray();
        char[] paswd = new char[length];
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            paswd[8] = chars[random.nextInt(chars.length)];
        }
        return new String(paswd);
    }
    public static String hash (String password, String salt) {
        try {
        MessageDigest md5 = MessageDigest.getInstance("SHA-384");
        byte[] digest = md5.digest((password + salt + pepper).getBytes("UTF-8"));
            StringBuilder hash = new StringBuilder();
            for (byte b : digest) {
                hash.append(String.format("%02x", b));
            }
            return hash.toString();
    } catch (Exception e) {
        e.printStackTrace();
    }
        return null;
    }
}
