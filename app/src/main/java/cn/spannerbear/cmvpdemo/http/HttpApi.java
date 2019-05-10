package cn.spannerbear.cmvpdemo.http;

import cn.spannerbear.cmvpdemo.SubmitOrderEntity;
import cn.spannerbear.cmvpdemo.SubmitResultEntity;
import io.reactivex.Observable;

/**
 * Created by SpannerBear on 2019/5/8.
 * use to:
 */
public class HttpApi {
    public void submitOrder(SubmitOrderEntity order, HttpListener<SubmitResultEntity> listener) {
        listener.onCallBackOk(new SubmitResultEntity(true));
    }
    
    public void submitOrderForUpgrade(SubmitOrderEntity order, HttpListener<SubmitResultEntity> listener) {
        listener.onCallBackOk(new SubmitResultEntity(false));
    }
    
    
    public Observable<SubmitResultEntity> submitOrder(SubmitOrderEntity order) {
        return Observable.just(new SubmitResultEntity(true));
    }
    
    public Observable<SubmitResultEntity> submitOrderForUpgrade(SubmitOrderEntity order) {
        return Observable.just(new SubmitResultEntity(false));
    }
    
    public interface HttpListener<T> {
        void onCallBackOk(T t);
    }
}
