package yuvlian.endstrogen.sdkserver.handlers.sdk;

import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import yuvlian.endstrogen.sdkserver.handlers.BaseHandler;

public class BatchEvent extends BaseHandler {
  public static class Request {}

  public static class ResponseData {}

  @Override
  public String[] routes() {
    return new String[] {"/batch_event"};
  }

  @Override
  public void post(HttpExchange x) throws IOException {
    String rsp = "OK";
    byte[] out = rsp.getBytes(StandardCharsets.UTF_8);
    setStatusCode(200);
    x.getResponseHeaders().add("Content-Type", "application/json");
    x.sendResponseHeaders(getStatusCode(), out.length);
    x.getResponseBody().write(out);
    x.close();
  }
}
