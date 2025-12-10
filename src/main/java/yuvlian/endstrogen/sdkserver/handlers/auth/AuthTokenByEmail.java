package yuvlian.endstrogen.sdkserver.handlers.auth;

import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import yuvlian.endstrogen.database.tables.Account;
import yuvlian.endstrogen.sdkserver.handlers.BaseHandler;
import yuvlian.endstrogen.sdkserver.handlers.BaseResponse;
import yuvlian.endstrogen.sdkserver.utils.BCryptHelper;
import yuvlian.endstrogen.sdkserver.utils.Parser;
import yuvlian.endstrogen.sdkserver.utils.TokenHelper;

public class AuthTokenByEmail extends BaseHandler {
  public static class Request {
    public String email;
    public Integer from;
    public String password;
  }

  public static class ResponseData {
    public String token;
  }

  @Override
  public String[] routes() {
    return new String[] {"/user/auth/v1/token_by_email_password"};
  }

  @Override
  public void post(HttpExchange x) throws IOException {
    BaseResponse<ResponseData> rsp = new BaseResponse<>();
    byte[] out;

    try {
      Request req = Parser.parseJsonBody(x, Request.class);
      // i do not give a shit
      String username = req.email;
      String password = req.password;
      Account acc = Account.DAO.getByUsername(username);

      // auto register
      if (acc == null) {
        String dispatchToken = TokenHelper.generateToken();
        String pwHash = BCryptHelper.hashPassword(password);
        String comboToken = TokenHelper.generateToken();

        rsp.data = new ResponseData();
        rsp.data.token = dispatchToken;

        Account.DAO.createAccount(username, pwHash, dispatchToken, comboToken);
      } else {
        String pwHash = acc.getPasswordHash();
        if (BCryptHelper.checkPassword(password, pwHash)) {
          int uid = acc.getUid();

          String oldToken = acc.getDispatchToken();
          rsp.data = new ResponseData();
          rsp.data.token = oldToken;

          if (TokenHelper.checkTokenExpiry(oldToken)) {
            String newToken = TokenHelper.generateToken();
            rsp.data.token = newToken;
            Account.DAO.updateDispatchTokenByUid(uid, newToken);
          }
        } else {
          rsp.status = 2;
          rsp.msg = "invalid password";
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
