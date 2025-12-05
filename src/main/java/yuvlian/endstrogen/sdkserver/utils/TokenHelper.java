package yuvlian.endstrogen.sdkserver.utils;

import java.security.SecureRandom;
import java.time.Instant;

public class TokenHelper {
  private static final SecureRandom secureRandom = new SecureRandom();
  private static final int randLen = 16;

  public static String generateToken() {
    byte[] randomBytes = new byte[randLen];
    secureRandom.nextBytes(randomBytes);

    StringBuilder sb = new StringBuilder(randLen * 2);
    for (byte b : randomBytes) {
      sb.append(String.format("%02X", b));
    }

    long timestamp = Instant.now().getEpochSecond();
    sb.append("+").append(timestamp);

    return sb.toString();
  }

  public static long parseTokenTimestamp(String token) throws Exception {
    int plusIndex = token.lastIndexOf('+');
    if (plusIndex < 0) {
      throw new Exception("invalid token format");
    }
    String tsPart = token.substring(plusIndex + 1);
    return Long.parseLong(tsPart);
  }

  public static boolean checkTokenExpiry(String token) throws Exception {
    return parseTokenTimestamp(token) < Instant.now().getEpochSecond();
  }
}
