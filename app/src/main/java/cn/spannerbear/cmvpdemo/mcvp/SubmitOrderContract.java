package cn.spannerbear.cmvpdemo.mcvp;

import cn.spannerbear.cmvpdemo.CallBack;
import cn.spannerbear.cmvpdemo.SubmitOrderEntity;

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
