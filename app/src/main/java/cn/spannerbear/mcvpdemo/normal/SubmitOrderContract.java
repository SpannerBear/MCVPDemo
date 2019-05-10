package cn.spannerbear.mcvpdemo.normal;

import cn.spannerbear.mcvpdemo.SubmitOrderEntity;

/**
 * Created by SpannerBear on 2019/5/8.
 * use to:
 */
public interface SubmitOrderContract {
    
    interface IView {
        void askUserForUpgrade(SubmitOrderEntity order);
        
        void onSubmitSucceed();
        
        void onSubmitFailed();
    }
    
    interface IPresenter {
        /**
         * 提交订单
         */
        void submitOrder(SubmitOrderEntity order);
        
        /**
         * 提交升级订单
         */
        void submitOnUpgrade(boolean isContinue, SubmitOrderEntity order);
    }
}
