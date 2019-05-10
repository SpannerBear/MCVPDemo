package cn.spannerbear.mcvpdemo.rxmcvp;

import android.annotation.SuppressLint;

import cn.spannerbear.mcvpdemo.SubmitOrderEntity;
import cn.spannerbear.mcvpdemo.SubmitResultEntity;
import cn.spannerbear.mcvpdemo.http.HttpApi;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

/**
 * Created by SpannerBear on 2019/5/8.
 * use to:
 */
public class SubmitOrderPresenter implements SubmitOrderContract.IPresenter {
    
    
    private SubmitOrderContract.IView mView;
    private HttpApi mApi;
    
    public SubmitOrderPresenter(SubmitOrderContract.IView view) {
        mView = view;
        mApi = new HttpApi();
    }
    
    @SuppressLint("CheckResult")
    @Override
    public void submitOrder(final SubmitOrderEntity order) {
        mApi.submitOrder(order)
                .flatMap(new Function<SubmitResultEntity, ObservableSource<SubmitResultEntity>>() {
                    @Override
                    public ObservableSource<SubmitResultEntity> apply(SubmitResultEntity result) throws Exception {
                        if (result.canUpDate) {
                            return getUpgradeOrderObservable(order);
                        } else {
                            return Observable.just(result);
                        }
                    }
                })
                .subscribe(new Consumer<SubmitResultEntity>() {
                    @Override
                    public void accept(SubmitResultEntity submitResultEntity) throws Exception {
                        mView.onSubmitSucceed();
                    }
                });
    }
    
    private Observable<SubmitResultEntity> getUpgradeOrderObservable(final SubmitOrderEntity order) {
        return mView.askUserForUpgrade()
                .take(1)
                .doOnNext(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean isUpgrade) throws Exception {
                        if (!isUpgrade) {
                            mView.onSubmitFailed();
                        }
                    }
                })
                .flatMap(new Function<Boolean, ObservableSource<SubmitResultEntity>>() {
                    @Override
                    public ObservableSource<SubmitResultEntity> apply(Boolean isUpgrade) throws Exception {
                        if (isUpgrade) {
                            return mApi.submitOrderForUpgrade(order);
                        } else {
                            return Observable.empty();
                        }
                    }
                });
    }
}
