package cn.spannerbear.mcvpdemo.mcvp;

import cn.spannerbear.mcvpdemo.CallBack;
import cn.spannerbear.mcvpdemo.SubmitOrderEntity;

/**
 * Created by SpannerBear on 2019/5/8.
 * use to:
 */
public interface SubmitOrderContract {
    
    interface IView {
        void askUserForUpgrade(CallBack<Boolean> callback);
        
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
