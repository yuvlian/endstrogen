package yuvlian.endstrogen.sdkserver.utils;

import com.sun.net.httpserver.HttpExchange;
import tools.jackson.databind.ObjectMapper;
import java.io.InputStream;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class Parser {
    private static final ObjectMapper mapper = new ObjectMapper();

    public static <T> T parseJsonBody(HttpExchange x, Class<T> cls) throws Exception {
        InputStream is = x.getRequestBody();
        return mapper.readValue(is, cls);
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
