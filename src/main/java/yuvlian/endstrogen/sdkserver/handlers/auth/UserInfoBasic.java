package yuvlian.endstrogen.sdkserver.handlers.auth;

import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import yuvlian.endstrogen.database.tables.Account;
import yuvlian.endstrogen.sdkserver.handlers.BaseHandler;
import yuvlian.endstrogen.sdkserver.handlers.BaseResponse;
import yuvlian.endstrogen.sdkserver.utils.Parser;

public class UserInfoBasic extends BaseHandler {
  // it's get && query
  // public static class Request {}

  public static class ResponseData {
    public String hdId;
    public String email;
    public String realEmail;
    public boolean isLatestUserAgreement;
    public String nickname;
  }

  @Override
  public String route() {
    return "/user/info/v1/basic";
  }

  @Override
  public void get(HttpExchange x) throws IOException {
    BaseResponse<ResponseData> rsp = new BaseResponse<>();
    byte[] out;

    try {
      String dispatchToken = Parser.parseQueryParams(x).get("token");
      if (dispatchToken == null) {
        rsp.status = 2;
        rsp.msg = "invalid request";
      } else {
        Account acc = Account.DAO.getByDispatchToken(dispatchToken);
        if (acc == null) {
          rsp.status = 2;
          rsp.msg = "Account not found";
        } else {
          int uid = acc.getUid();
          String username = acc.getUsername();

          rsp.data = new ResponseData();
          rsp.data.hdId = Integer.toString(uid);
          rsp.data.email = username;
          rsp.data.realEmail = username;
          rsp.data.isLatestUserAgreement = true;
          rsp.data.nickname = username;
        }
      }
    } catch (Exception e) {
      rsp.status = 2;
      rsp.msg = e.toString();
    } finally {
      setStatusCode(200);
      out = Parser.toJsonString(rsp).getBytes(StandardCharsets.UTF_8);
      x.getResponseHeaders().add("Content-Type", "application/json");
      x.sendResponseHeaders(getStatusCode(), out.length);
      x.getResponseBody().write(out);
      x.close();
    }
  }
}
