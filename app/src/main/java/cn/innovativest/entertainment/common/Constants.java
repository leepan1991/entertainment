package cn.innovativest.entertainment.common;

/**
 * 常量类
 */
public class Constants {

    public static final String CLIENT = "android";
    public static final String PACKAGE = "cc.ahxb.hlg";
    public static final String VER = "1.0.1";
    /**
     * http请求
     */
    //请求成功码
    public static final int HTTP_RESULTOK = 0;
    //服务器地址
    public static final String HTTP_BASE_URL = "";
    //请求超时时间
    public static final int HTTP_TIMEOUT_TIME = 30;

    /**
     * HTTP请求异常状态码
     */
    //请求超时
    public static final int HTTP_FAILURE_TIMEOUT = -100;
    //没有网络
    public static final int HTTP_FAILURE_NETWORK = -101;
    //连接异常
    public static final int HTTP_FAILURE_CONN = -102;
    //Token失效
    public static final int HTTP_FAILURE_TOKEN = 400;

    public static final int NONE = 0;
    public static final int ERR_USERNAME = 1;
    public static final int ERR_PASSWORD = 2;
    public static final int ERR_POWER = 3;
    public static final int ERR_API = 4;
    public static final int ERR_VERSION = 5;
    public static final int ERR_NO_SENSORTYPE = 6;
    public static final int ERR_NO_SENSOR = 7;
    public static final int ERR_SERVER_INTERNAL_ERROR = 8;
    public static final int ERR_ACCESS_TYPE = 9;
}
