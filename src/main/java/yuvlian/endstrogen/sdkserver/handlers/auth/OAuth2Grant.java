package yuvlian.endstrogen.sdkserver.handlers.auth;

import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import yuvlian.endstrogen.sdkserver.handlers.BaseHandler;

public class OAuth2Grant extends BaseHandler {
  public static class Request {}

  public static class ResponseData {}

  @Override
  public String route() {
    return "/user/oauth2/v2/grant";
  }

  @Override
  public void post(HttpExchange x) throws IOException {}
}
