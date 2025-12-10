package yuvlian.endstrogen.sdkserver.handlers.sdk;

import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import yuvlian.endstrogen.sdkserver.handlers.BaseHandler;
import yuvlian.endstrogen.sdkserver.handlers.BaseResponse;
import yuvlian.endstrogen.sdkserver.utils.Parser;

public class GameBulletinVer extends BaseHandler {
  public static class Request {}

  public static class ResponseData {
    public int version;
  }

  @Override
  public String[] routes() {
    return new String[] {"/api/gameBulletin/version"};
  }

  @Override
  public void post(HttpExchange x) throws IOException {
    BaseResponse<ResponseData> rsp = new BaseResponse<>();
    rsp.msg = "";
    rsp.data = new ResponseData();
    rsp.data.version = 30;
    byte[] out = Parser.toJsonString(rsp).getBytes(StandardCharsets.UTF_8);
    setStatusCode(200);
    x.getResponseHeaders().add("Content-Type", "application/json");
    x.sendResponseHeaders(getStatusCode(), out.length);
    x.getResponseBody().write(out);
    x.close();
  }
}
