package cn.innovativest.entertainment.bean;

import java.io.Serializable;

public class TabBean implements Serializable {


    private String soupian;
    private String dianshi;
    private String shouye;
    private String qiupian;

    public String getSoupian() {
        return soupian;
    }

    public void setSoupian(String soupian) {
        this.soupian = soupian;
    }

    public String getDianshi() {
        return dianshi;
    }

    public void setDianshi(String dianshi) {
        this.dianshi = dianshi;
    }

    public String getShouye() {
        return shouye;
    }

    public void setShouye(String shouye) {
        this.shouye = shouye;
    }

    public String getQiupian() {
        return qiupian;
    }

    public void setQiupian(String qiupian) {
        this.qiupian = qiupian;
    }
}
