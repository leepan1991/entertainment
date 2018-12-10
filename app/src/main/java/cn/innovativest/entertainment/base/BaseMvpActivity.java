package cn.innovativest.entertainment.base;

import cn.innovativest.entertainment.utils.ToastUtils;

/**
 * MVP模式-BaseMvpActivity
 */
public abstract class BaseMvpActivity<V, P extends BasePresent<V>> extends BaseActivity implements BaseView {
    protected P mPresenter;

    @Override
    public void initBasePresenter() {
        mPresenter = initPresenter();
        mPresenter.attach((V) this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detach();
        }
    }

    public abstract P initPresenter();

    @Override
    public void toast(CharSequence s) {
        ToastUtils.showShort(this, s);
    }

    @Override
    public void showDialogProgress() {

    }

    @Override
    public void dismissDialogProgress() {

    }

    @Override
    public void showSucessPage() {

    }

    @Override
    public void showLoadingPage() {

    }

    @Override
    public void showEmptyPage() {

    }

    @Override
    public void showErrorPage() {

    }

    @Override
    public void showNetWorkErrorPage() {

    }
}
