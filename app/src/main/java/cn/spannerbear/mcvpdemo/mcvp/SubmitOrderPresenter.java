package cn.spannerbear.mcvpdemo.mcvp;

import cn.spannerbear.mcvpdemo.CallBack;
import cn.spannerbear.mcvpdemo.SubmitResultEntity;
import cn.spannerbear.mcvpdemo.SubmitOrderEntity;
import cn.spannerbear.mcvpdemo.http.HttpApi;

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
    
    @Override
    public void submitOrder(final SubmitOrderEntity order) {
        mApi.submitOrder(order, new HttpApi.HttpListener<SubmitResultEntity>() {
            @Override
            public void onCallBackOk(SubmitResultEntity result) {
                if (result.canUpDate) {
                    mView.askUserForUpgrade(new CallBack<Boolean>() {
                        @Override
                        public void onResult(Boolean p) {
                            submitOnUpgrade(p, order);
                        }
                    });
                } else {
                    mView.onSubmitSucceed();
                }
            }
        });
    }
    
    private void submitOnUpgrade(boolean isContinue, SubmitOrderEntity order) {
        if (!isContinue) {
            mView.onSubmitFailed();
            return;
        }
        mApi.submitOrderForUpgrade(order, new HttpApi.HttpListener<SubmitResultEntity>() {
            @Override
            public void onCallBackOk(SubmitResultEntity submitResultEntity) {
                mView.onSubmitSucceed();
            }
        });
    }
}
