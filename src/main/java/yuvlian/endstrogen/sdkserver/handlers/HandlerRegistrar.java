package yuvlian.endstrogen.sdkserver.handlers;

import com.sun.net.httpserver.HttpServer;
import yuvlian.endstrogen.sdkserver.utils.Logger;
import yuvlian.endstrogen.sdkserver.handlers.auth.*;

public class HandlerRegistrar {
    private static void register(HttpServer sv, BaseHandler[] handlers) {
        for (BaseHandler handler : handlers) {
            sv.createContext(handler.route(), new Logger(handler));
        }
    }

    public static void registerAuthHandlers(HttpServer sv) {
        register(sv, new BaseHandler[] {
            new AuthGrant(),
            new AuthTokenByChannel(),
            new AuthTokenByEmail(),
            new CheckWhitelist(),
            new OAuth2Grant(),
            new QueryZoneWhitelist(),
            new UserInfoBasic()
        });
    }

    public static void registerSdkHandlers(HttpServer sv) {

    }
}
