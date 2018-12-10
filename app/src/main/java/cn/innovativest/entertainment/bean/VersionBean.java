package cn.innovativest.entertainment.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class VersionBean implements Serializable {

    /**
     * ID : 4
     * Ver : 1.0.1
     * isForced : 1
     * Url : https://itunes.apple.com/cn/app/欢乐购淘宝购物优惠券/id1377149285?mt=12
     */

    @SerializedName("ID")
    private String id;
    @SerializedName("Ver")
    private String verName;
    private String isForced;
    @SerializedName("Url")
    private String apkUrl;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVerName() {
        return verName;
    }

    public void setVerName(String verName) {
        this.verName = verName;
    }

    public String getIsForced() {
        return isForced;
    }

    public void setIsForced(String isForced) {
        this.isForced = isForced;
    }

    public String getApkUrl() {
        return apkUrl;
    }

    public void setApkUrl(String apkUrl) {
        this.apkUrl = apkUrl;
    }
}
