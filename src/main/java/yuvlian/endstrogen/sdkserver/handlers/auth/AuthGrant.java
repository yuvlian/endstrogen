package yuvlian.endstrogen.sdkserver.handlers.auth;

import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import yuvlian.endstrogen.database.tables.Account;
import yuvlian.endstrogen.sdkserver.handlers.BaseHandler;
import yuvlian.endstrogen.sdkserver.handlers.BaseResponse;
import yuvlian.endstrogen.sdkserver.utils.Parser;
import yuvlian.endstrogen.sdkserver.utils.TokenHelper;

public class AuthGrant extends BaseHandler {
  public static class Request {
    public String token;
  }

  public static class ResponseData {
    public String uid;
    public String code;
  }

  @Override
  public String route() {
    return "/u8/user/auth/v2/grant";
  }

  @Override
  public void post(HttpExchange x) throws IOException {
    BaseResponse<ResponseData> rsp = new BaseResponse<>();
    byte[] out;

    try {
      Request req = Parser.parseJsonBody(x, Request.class);
      String oldToken = req.token;
      Account acc = Account.DAO.getByComboToken(oldToken);

      if (acc == null) {
        rsp.status = 2;
        rsp.msg = "Account not found";
      } else {
        int uid = acc.getUid();

        rsp.data = new ResponseData();
        rsp.data.uid = Integer.toString(uid);
        rsp.data.code = oldToken;

        // not sure if you should refresh here
        if (TokenHelper.checkTokenExpiry(oldToken)) {
          String newToken = TokenHelper.generateToken();
          rsp.data.code = newToken;
          Account.DAO.updateComboTokenByUid(uid, newToken);
        }
      }
    } catch (Exception e) {
      rsp.status = 2;
      rsp.msg = e.toString();
    } finally {
      // i assume setting 500 wont make msg show ingame.
      // no point setting manually for each branch.
      setStatusCode(200);
      out = Parser.toJsonString(rsp).getBytes(StandardCharsets.UTF_8);
      x.getResponseHeaders().add("Content-Type", "application/json");
      x.sendResponseHeaders(getStatusCode(), out.length);
      x.getResponseBody().write(out);
      x.close();
    }
  }
}
