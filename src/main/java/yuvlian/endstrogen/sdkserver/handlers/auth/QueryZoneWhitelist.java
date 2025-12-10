package yuvlian.endstrogen.sdkserver.handlers.auth;

import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import yuvlian.endstrogen.sdkserver.handlers.BaseHandler;
import yuvlian.endstrogen.sdkserver.handlers.BaseResponse;
import yuvlian.endstrogen.sdkserver.utils.Parser;

public class QueryZoneWhitelist extends BaseHandler {
  public static class Request {}

  public static class ResponseData {
    public String[] zone;
  }

  @Override
  public String route() {
    return "/game/user/v1/query_zone_whitelist";
  }

  @Override
  public void get(HttpExchange x) throws IOException {
    BaseResponse<ResponseData> rsp = new BaseResponse<>();
    rsp.data = new ResponseData();
    rsp.data.zone = new String[] {"EUAndUS"};
    byte[] out = Parser.toJsonString(rsp).getBytes(StandardCharsets.UTF_8);
    setStatusCode(200);
    x.getResponseHeaders().add("Content-Type", "application/json");
    x.sendResponseHeaders(getStatusCode(), out.length);
    x.getResponseBody().write(out);
    x.close();
  }
}
