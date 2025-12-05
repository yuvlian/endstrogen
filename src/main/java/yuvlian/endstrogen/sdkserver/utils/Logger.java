package yuvlian.endstrogen.sdkserver.utils;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import yuvlian.endstrogen.sdkserver.handlers.BaseHandler;

public class Logger implements HttpHandler {
  private final BaseHandler next;

  public Logger(BaseHandler next) {
    this.next = next;
  }

  @Override
  public void handle(HttpExchange x) throws IOException {
    try {
      next.handle(x);
    } finally {
      int statusCode = next.getStatusCode();
      String method = x.getRequestMethod();
      String uri = x.getRequestURI().toString();
      System.out.printf("%d %s %s%n", statusCode, method, uri);
    }
  }
}
