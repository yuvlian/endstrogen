package yuvlian.endstrogen.sdkserver;

import com.sun.net.httpserver.HttpServer;
import java.net.InetSocketAddress;
import yuvlian.endstrogen.sdkserver.handlers.HandlerRegistrar;

public class SdkServer {
    public static void start() throws Exception {
        System.out.println("SdkServer @ http://127.0.0.1:21000");
        HttpServer sv = HttpServer.create(new InetSocketAddress("127.0.0.1", 21000), 5);
        HandlerRegistrar.registerAuthHandlers(sv);
        HandlerRegistrar.registerSdkHandlers(sv);
        sv.setExecutor(null);
        sv.start();
    }
}
