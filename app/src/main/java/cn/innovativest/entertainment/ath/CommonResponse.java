package cn.innovativest.entertainment.ath;

import com.google.gson.annotations.SerializedName;


public class CommonResponse extends BaseResponse {

//    @SerializedName("data")
//    public ArrayList<CommonItem> commonItems;

    @SerializedName("data")
    public CommonItem commonItem;

}
