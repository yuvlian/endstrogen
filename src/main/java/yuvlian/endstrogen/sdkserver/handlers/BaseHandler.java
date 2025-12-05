package yuvlian.endstrogen.sdkserver.handlers;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public abstract class BaseHandler implements HttpHandler {
    public abstract String route();

    protected int statusCode = 200;

    protected void setStatus(int code) {
        this.statusCode = code;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void get(HttpExchange x) throws IOException {
        methodNotAllowed(x);
    }

    public void post(HttpExchange x) throws IOException {
        methodNotAllowed(x);
    }

    public void put(HttpExchange x) throws IOException {
        methodNotAllowed(x);
    }

    public void delete(HttpExchange x) throws IOException {
        methodNotAllowed(x);
    }

    public void patch(HttpExchange x) throws IOException {
        methodNotAllowed(x);
    }

    public void head(HttpExchange x) throws IOException {
        methodNotAllowed(x);
    }

    public void options(HttpExchange x) throws IOException {
        methodNotAllowed(x);
    }

    @Override
    public void handle(HttpExchange x) throws IOException {
        switch (x.getRequestMethod()) {
            case "GET": get(x); break;
            case "POST": post(x); break;
            case "PUT": put(x); break;
            case "DELETE": delete(x); break;
            case "PATCH": patch(x); break;
            case "HEAD": head(x); break;
            case "OPTIONS": options(x); break;
            default: methodNotAllowed(x);
        }
    }

    public void methodNotAllowed(HttpExchange x) throws IOException {
        byte[] msg = "Method Not Allowed".getBytes(StandardCharsets.UTF_8);
        x.sendResponseHeaders(405, msg.length);
        x.getResponseBody().write(msg);
        x.close();
        setStatus(405);
    }
}
