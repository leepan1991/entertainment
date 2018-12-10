package cn.innovativest.entertainment.base;

import android.util.Log;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * MVP模式-BasePresent
 */
public abstract class BasePresent<V> {

    private CompositeDisposable mCd;
    public V view;

    public void attach(V view) {
        this.view = view;
    }

    public void detach() {
        this.view = null;
        if (mCd != null) {
            if (!mCd.isDisposed()) mCd.isDisposed();
            mCd = null;
        }

    }


    public CompositeDisposable getMCD() {
        if (mCd == null || mCd.isDisposed()) {
            mCd = new CompositeDisposable();
        }
        return mCd;
    }

    protected <T> void add(Observable<T> observable, Consumer<T> success) {
        getMCD().add(observable
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(success, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.d("http", "throwable: " + throwable.getMessage());
                    }
                }));
    }

    protected <T> void add(Observable<T> observable, Consumer<T> success, Consumer<? super Throwable> error) {
        getMCD().add(observable
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(success, error));
    }

}
