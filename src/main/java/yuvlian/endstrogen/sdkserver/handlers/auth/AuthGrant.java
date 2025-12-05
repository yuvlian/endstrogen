package yuvlian.endstrogen.sdkserver.handlers.auth;

import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import yuvlian.endstrogen.sdkserver.handlers.BaseHandler;
import yuvlian.endstrogen.sdkserver.handlers.BaseResponse;
import yuvlian.endstrogen.sdkserver.utils.Parser;

public class AuthGrant extends BaseHandler {
    public static class Request {}

    public static class ResponseData {}

    @Override
    public String route() {
        return "/u8/user/auth/v2/grant";
    }

    @Override
    public void get(HttpExchange x) throws IOException {}

    @Override
    public void post(HttpExchange x) throws IOException {}
}
