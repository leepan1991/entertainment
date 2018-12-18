package cn.innovativest.entertainment.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class EBuyBean {
    @SerializedName("data")
    private List<BuyBean> lstBuyBeans;
    private String text;
    @SerializedName("user_info")
    private UserInfoBean userInfoBean;

    public List<BuyBean> getLstBuyBeans() {
        return lstBuyBeans;
    }

    public void setLstBuyBeans(List<BuyBean> lstBuyBeans) {
        this.lstBuyBeans = lstBuyBeans;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public UserInfoBean getUserInfoBean() {
        return userInfoBean;
    }

    public void setUserInfoBean(UserInfoBean userInfoBean) {
        this.userInfoBean = userInfoBean;
    }
}
