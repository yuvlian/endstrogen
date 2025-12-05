package yuvlian.endstrogen.sdkserver.handlers.auth;

import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import yuvlian.endstrogen.sdkserver.handlers.BaseHandler;
import yuvlian.endstrogen.sdkserver.handlers.BaseResponse;
import yuvlian.endstrogen.sdkserver.utils.Parser;

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
    public String route() {
        return "/user/auth/v1/token_by_email_password";
    }

    @Override
    public void post(HttpExchange x) throws IOException {
        try {
            Request req = Parser.parseJsonBody(x, Request.class);
            ResponseData data = new ResponseData();
            data.token = req.password;

            BaseResponse<ResponseData> rsp = new BaseResponse<>();
            rsp.data = data;

            String json = Parser.toJsonString(rsp);
            byte[] out = json.getBytes(StandardCharsets.UTF_8);

            x.getResponseHeaders().add("Content-Type", "application/json");
            x.sendResponseHeaders(200, out.length);
            x.getResponseBody().write(out);
            x.close();
            setStatus(200);
        } catch (Exception e) {
            BaseResponse<Object> rsp = new BaseResponse<>();
            rsp.status = 67; // funny number
            rsp.msg = "curl properly dumbass: " + e.toString();
            rsp.type = "X";

            String json = Parser.toJsonString(rsp);
            byte[] out = json.getBytes(StandardCharsets.UTF_8);

            x.getResponseHeaders().add("Content-Type", "application/json");
            x.sendResponseHeaders(400, out.length);
            x.getResponseBody().write(out);
            x.close();
            setStatus(400);
        }
    }
}
