package cn.innovativest.entertainment.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;


/**
 * MVP模式-BaseMvpFragment
 */
public abstract class BaseMvpFragment<V, P extends BasePresent<V>> extends BaseFragment {

    protected P mPresenter;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mPresenter = initPresenter();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter.attach((V) this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mPresenter!=null){
            mPresenter.detach();
        }
    }

    public abstract P initPresenter();

}
