package yuvlian.endstrogen;

import yuvlian.endstrogen.database.Database;
import yuvlian.endstrogen.sdkserver.SdkServer;

public class App {
  public static void main(String[] args) throws Exception {
    Database.close();
    SdkServer.start();
  }
}
