package yuvlian.endstrogen.sdkserver.handlers;

import com.sun.net.httpserver.HttpServer;
import yuvlian.endstrogen.sdkserver.handlers.auth.*;
import yuvlian.endstrogen.sdkserver.handlers.sdk.*;
import yuvlian.endstrogen.sdkserver.utils.Logger;

public class HandlerRegistrar {
  private static void register(HttpServer sv, BaseHandler[] handlers) {
    for (BaseHandler handler : handlers) {
      for (String route : handler.routes()) {
        sv.createContext(route, new Logger(handler));
      }
    }
  }

  public static void registerAuthHandlers(HttpServer sv) {
    register(
        sv,
        new BaseHandler[] {
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
    register(
        sv,
        new BaseHandler[] {
          new AppMeta(), new AppConfig(), new BatchEvent(), new GameBulletinVer(),
        });
  }
}
