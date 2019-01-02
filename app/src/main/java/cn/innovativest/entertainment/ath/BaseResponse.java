package cn.innovativest.entertainment.ath;

import com.google.gson.annotations.SerializedName;

public class BaseResponse {

    @SerializedName("status")
    public int status;
    @SerializedName("message")
    public String message;
}
