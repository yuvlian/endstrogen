package yuvlian.endstrogen.sdkserver.utils;

import at.favre.lib.crypto.bcrypt.BCrypt;
import java.nio.charset.StandardCharsets;

public class BCryptHelper {
    private static final BCrypt.Hasher bcryptHasher = BCrypt.withDefaults();
    private static final BCrypt.Verifyer bcryptVerifyer = BCrypt.verifyer();

    public static String hashPassword(String pw) {
        return new String(bcryptHasher.hash(12, pw.getBytes(StandardCharsets.UTF_8)));
    }

    public static boolean checkPassword(String pw, String hash) {
        BCrypt.Result result = bcryptVerifyer.verify(
            pw.getBytes(StandardCharsets.UTF_8), hash.getBytes(StandardCharsets.UTF_8));
        return result.verified;
    }
}
