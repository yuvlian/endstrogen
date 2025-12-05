package yuvlian.endstrogen.sdkserver.handlers.auth;

import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import yuvlian.endstrogen.database.tables.Account;
import yuvlian.endstrogen.sdkserver.handlers.BaseHandler;
import yuvlian.endstrogen.sdkserver.handlers.BaseResponse;
import yuvlian.endstrogen.sdkserver.utils.Parser;
import yuvlian.endstrogen.sdkserver.utils.TokenHelper;

public class AuthTokenByChannel extends BaseHandler {
  public static class Request {
    // i hate people who put json string in request instead of just json
    // fuck you if you do this
    public String channelToken;
  }

  public static class ChannelTokenBody {
    public String Code;
    public String Type;
    public Boolean IsSuc;
  }

  public static class ResponseData {
    public String token;
  }

  @Override
  public String route() {
    return "/u8/user/auth/v2/token_by_channel_token";
  }

  @Override
  public void post(HttpExchange x) throws IOException {
    BaseResponse<ResponseData> rsp = new BaseResponse<>();
    byte[] out;

    try {
      Request req = Parser.parseJsonBody(x, Request.class);
      ChannelTokenBody inner = Parser.parseJsonString(req.channelToken, ChannelTokenBody.class);
      String oldToken = inner.Code;
      Account acc = Account.DAO.getByComboToken(oldToken);

      if (acc == null) {
        rsp.status = 2;
        rsp.msg = "Account not found";
      } else {
        int uid = acc.getUid();

        rsp.data = new ResponseData();
        rsp.data.token = oldToken;

        // not sure if you should refresh here
        if (TokenHelper.checkTokenExpiry(oldToken)) {
          String newToken = TokenHelper.generateToken();
          rsp.data.token = newToken;
          Account.DAO.updateComboTokenByUid(uid, newToken);
        }
      }
    } catch (Exception e) {
      rsp.status = 2;
      rsp.msg = e.toString();
    } finally {
      // i assume setting 500 wont make msg show ingame.
      // no point setting manually for each branch.
      setStatus(200);
      out = Parser.toJsonString(rsp).getBytes(StandardCharsets.UTF_8);
      x.getResponseHeaders().add("Content-Type", "application/json");
      x.sendResponseHeaders(getStatusCode(), out.length);
      x.getResponseBody().write(out);
      x.close();
    }
  }
}
