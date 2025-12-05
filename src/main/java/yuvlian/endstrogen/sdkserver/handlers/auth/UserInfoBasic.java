package yuvlian.endstrogen.sdkserver.handlers.auth;

import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import yuvlian.endstrogen.sdkserver.handlers.BaseHandler;

public class UserInfoBasic extends BaseHandler {
  public static class Request {}

  public static class ResponseData {}

  @Override
  public String route() {
    return "/user/info/v1/basic";
  }

  @Override
  public void get(HttpExchange x) throws IOException {}
}
