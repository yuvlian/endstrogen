package yuvlian.endstrogen.sdkserver.handlers;

public class BaseResponse<T> {
  public int status = 0;
  public String msg = "OK";
  public String type = "A";
  public T data = null;
}
