package yuvlian.endstrogen.sdkserver.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import yuvlian.endstrogen.sdkserver.utils.Logger;
import yuvlian.endstrogen.sdkserver.handlers.auth.AuthTokenByEmail;

public class HandlerRegistrar {
    private static void register(HttpServer sv, BaseHandler handler) {
        sv.createContext(handler.route(), new Logger(handler));
    }

    public static void registerAuthHandlers(HttpServer sv) {
        register(sv, new AuthTokenByEmail());
    }

    public static void registerSdkHandlers(HttpServer sv) {
        
    }
}
