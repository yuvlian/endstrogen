package yuvlian.endstrogen.sdkserver.utils;

import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import tools.jackson.databind.ObjectMapper;

public class Parser {
  private static final ObjectMapper mapper = new ObjectMapper();

  public static <T> T parseJsonBody(HttpExchange x, Class<T> cls) throws IOException {
    return mapper.readValue(x.getRequestBody(), cls);
  }

  public static <T> T parseJsonString(String json, Class<T> cls) throws IOException {
    return mapper.readValue(json, cls);
  }

  public static <T> String toJsonString(T obj) throws IOException {
    return mapper.writeValueAsString(obj);
  }

  public static Map<String, String> parseQueryParams(HttpExchange x) {
    String rq = x.getRequestURI().getRawQuery();
    Map<String, String> result = new HashMap<>();

    if (rq == null || rq.isEmpty()) return result;

    for (String pair : rq.split("&")) {
      String[] parts = pair.split("=", 2);
      String k = urlDecode(parts[0]);
      String v = parts.length > 1 ? urlDecode(parts[1]) : "";
      result.put(k, v);
    }

    return result;
  }

  private static String urlDecode(String s) {
    return URLDecoder.decode(s, StandardCharsets.UTF_8);
  }
}
