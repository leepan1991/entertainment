package cn.innovativest.entertainment.rxbase;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by Administrator on 2018/3/1.
 */

public abstract class BaseObserver<T> implements Observer<T> {

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(T value) {
        onRequestSuccess(value);
    }

    @Override
    public void onError(Throwable e) {
        onRequestError(e);
    }

    @Override
    public void onComplete() {

    }

    public abstract void onRequestSuccess(T value);

    public abstract void onRequestError(Throwable e);
}
