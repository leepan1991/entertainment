package cn.innovativest.entertainment.base;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * BottomSheetDialogFragment基类MVP模式
 * Created by gh on 2017/7/10.
 */

public abstract class BaseMvpBottomSheetDialogFragment<V, P extends BasePresent<V>> extends
        BaseBottomSheetDialogFragment {
    protected P mPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPresenter = initPresenter();
        mPresenter.attach((V) this);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);

    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if (mPresenter != null) {
            mPresenter.detach();
        }
    }

    public abstract P initPresenter();

}
