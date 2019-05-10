package cn.spannerbear.mcvpdemo.rxmcvp;

import cn.spannerbear.mcvpdemo.SubmitOrderEntity;
import io.reactivex.Observable;

/**
 * Created by SpannerBear on 2019/5/8.
 * use to:
 */
public interface SubmitOrderContract {
    
    interface IView {
        Observable<Boolean> askUserForUpgrade();
        
        void onSubmitSucceed();
    
        void onSubmitFailed();
    }
    
    interface IPresenter {
        /**
         * 提交订单
         */
        void submitOrder(SubmitOrderEntity order);
        
    }
}
