package yuvlian.endstrogen.sdkserver.utils;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import yuvlian.endstrogen.sdkserver.handlers.BaseHandler;

public class HandlerRegistrar {
    private static void register(HttpServer sv, BaseHandler handler) {
        sv.createContext(handler.route(), new Logger(handler));
    }

    public static void registerAuthHandlers(HttpServer sv) {

    }

    public static void registerSdkHandlers(HttpServer sv) {
        
    }
}
