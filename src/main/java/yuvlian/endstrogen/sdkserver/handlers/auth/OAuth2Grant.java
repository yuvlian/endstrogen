package yuvlian.endstrogen.sdkserver.handlers.auth;

import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import yuvlian.endstrogen.database.tables.Account;
import yuvlian.endstrogen.sdkserver.handlers.BaseHandler;
import yuvlian.endstrogen.sdkserver.handlers.BaseResponse;
import yuvlian.endstrogen.sdkserver.utils.Parser;
import yuvlian.endstrogen.sdkserver.utils.TokenHelper;

public class OAuth2Grant extends BaseHandler {
  public static class Request {
    public String token;
  }

  public static class ResponseData {
    public String uid;
    public String code;
  }

  @Override
  public String route() {
    return "/user/oauth2/v2/grant";
  }

  @Override
  public void post(HttpExchange x) throws IOException {
    BaseResponse<ResponseData> rsp = new BaseResponse<>();
    byte[] out;

    try {
      Request req = Parser.parseJsonBody(x, Request.class);
      String dispatchToken = req.token;
      Account acc = Account.DAO.getByDispatchToken(dispatchToken);

      if (acc == null) {
        rsp.status = 2;
        rsp.msg = "Account not found";
      } else {
        int uid = acc.getUid();
        String comboToken = TokenHelper.generateToken();
        Account.DAO.updateComboTokenByUid(uid, comboToken);
        rsp.data = new ResponseData();
        rsp.data.uid = Integer.toString(uid);
        rsp.data.code = comboToken;
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
