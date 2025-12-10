package yuvlian.endstrogen.sdkserver.handlers.sdk;

import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import yuvlian.endstrogen.sdkserver.handlers.BaseHandler;
import yuvlian.endstrogen.sdkserver.handlers.BaseResponse;
import yuvlian.endstrogen.sdkserver.utils.Parser;

public class AppConfig extends BaseHandler {
  public static class Request {}

  public static class ResponseData {
    public AgreementUrl agreementUrl;
    public AppData app;
    public String customerServiceUrl;
    public String thirdPartyRedirectUrl;
    public ScanUrl scanUrl;
    public String[] loginChannels;
    public String userCenterUrl;
  }

  public static class AgreementUrl {
    public String register;
    public String privacy;
    public String unbind;
    public String account;
    public String game;
  }

  public static class AppData {
    public String googleAndroidClientId;
    public String googleIosClientId;
    public boolean enableAutoLogin;
    public boolean enablePayment;
    public boolean enableGuestRegister;
    public boolean needShowName;
    public Map<String, String> displayName;
    public String[] unbindAgreement;
    public int unbindLimitedDays;
    public int unbindCoolDownDays;
    public String customerServiceUrl;
    public boolean enableUnbindGrant;
  }

  public static class ScanUrl {
    public String login;
  }

  @Override
  public String[] routes() {
    return new String[] {"/app/v1/config"};
  }

  @Override
  public void get(HttpExchange x) throws IOException {
    BaseResponse<ResponseData> rsp = new BaseResponse<>();
    byte[] out;
    ResponseData data = new ResponseData();

    data.agreementUrl = new AgreementUrl();
    data.agreementUrl.register =
        "https://user.gryphline.com/{language}/protocol/plain/terms_of_service";
    data.agreementUrl.privacy =
        "https://user.gryphline.com/{language}/protocol/plain/privacy_policy";
    data.agreementUrl.unbind =
        "https://user.gryphline.com/{language}/protocol/plain/endfield/privacy_policy";
    data.agreementUrl.account =
        "https://user.gryphline.com/{language}/protocol/plain/terms_of_service";
    data.agreementUrl.game =
        "https://user.gryphline.com/{language}/protocol/plain/endfield/privacy_policy";

    data.app = new AppData();
    data.app.googleAndroidClientId = "";
    data.app.googleIosClientId = "";
    data.app.enableAutoLogin = true;
    data.app.enablePayment = true;
    data.app.enableGuestRegister = false;
    data.app.needShowName = true;

    data.app.displayName = new HashMap<>();
    data.app.displayName.put("en-us", "Arknights: Endfield");
    data.app.displayName.put("ja-jp", "アークナイツ：エンドフィールド");
    data.app.displayName.put("ko-kr", "명일방주：엔드필드");
    data.app.displayName.put("zh-cn", "明日方舟：终末地");
    data.app.displayName.put("zh-tw", "明日方舟：終末地");

    data.app.unbindAgreement = new String[] {};
    data.app.unbindLimitedDays = 30;
    data.app.unbindCoolDownDays = 14;
    data.app.customerServiceUrl =
        "https://gryphline.helpshift.com/hc/{language}/4-arknights-endfield";
    data.app.enableUnbindGrant = false;

    data.customerServiceUrl = "https://gryphline.helpshift.com/hc/{language}/4-arknights-endfield";
    data.thirdPartyRedirectUrl = "https://web-api.gryphline.com/callback/thirdPartyAuth.html";

    data.scanUrl = new ScanUrl();
    data.scanUrl.login = "yj://scan_login";

    data.loginChannels = new String[] {};
    data.userCenterUrl = "https://user.gryphline.com/pcSdk/userInfo?language={language}";

    rsp.data = data;

    setStatusCode(200);
    out = Parser.toJsonString(rsp).getBytes(StandardCharsets.UTF_8);
    x.getResponseHeaders().add("Content-Type", "application/json");
    x.sendResponseHeaders(getStatusCode(), out.length);
    x.getResponseBody().write(out);
    x.close();
  }
}
