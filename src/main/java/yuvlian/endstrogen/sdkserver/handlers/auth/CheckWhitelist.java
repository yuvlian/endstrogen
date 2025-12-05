package yuvlian.endstrogen.sdkserver.handlers.auth;

import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import yuvlian.endstrogen.sdkserver.handlers.BaseHandler;

public class CheckWhitelist extends BaseHandler {
  public static class Request {}

  public static class ResponseData {}

  @Override
  public String route() {
    return "/user/oauth2/v1/check_whitelist";
  }

  @Override
  public void get(HttpExchange x) throws IOException {}
}
