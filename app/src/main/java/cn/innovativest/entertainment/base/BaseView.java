package cn.innovativest.entertainment.base;

/**
 * MVP模式-BaseView
 */
public interface BaseView {

    /**
     * Toast
     *
     * @param s
     */
    void toast(CharSequence s);


    /**
     * loading对话框
     */
    void showDialogProgress();

    void dismissDialogProgress();

    /**
     * 显示成功页面
     */
    void showSucessPage();
    /**
     * 显示loading页
     */
    void showLoadingPage();

    /**
     * 显示空数据页面
     */
    void showEmptyPage();

    /**
     * 显示数据错误页面
     */
    void showErrorPage();

    /**
     * 显示网络异常页面
     */
    void showNetWorkErrorPage();


}
