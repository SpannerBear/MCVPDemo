package cn.spannerbear.cmvpdemo.rxmcvp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import cn.spannerbear.cmvpdemo.InquiryDialog;
import cn.spannerbear.cmvpdemo.R;
import cn.spannerbear.cmvpdemo.SubmitOrderEntity;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;

public class SubmitOrderActivity extends AppCompatActivity implements SubmitOrderContract.IView {
    
    
    private SubmitOrderPresenter mSubmitOrderPresenter;
    private InquiryDialog mDialog;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_order);
        mSubmitOrderPresenter = new SubmitOrderPresenter(this);
        findViewById(R.id.btn_submit)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mSubmitOrderPresenter.submitOrder(new SubmitOrderEntity());
                    }
                });
    }
    
    @Override
    public Observable<Boolean> askUserForUpgrade() {
        if (mDialog == null) {
            mDialog = new InquiryDialog(this);
        }
        Observable<Boolean> booleanObservable = Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(final ObservableEmitter<Boolean> emitter) throws Exception {
                mDialog.setOnResultListener(new InquiryDialog.OnResultListener() {
                    @Override
                    public void onResult(boolean result) {
                        mDialog.dismiss();
                        emitter.onNext(result);
                        emitter.onComplete();
                    }
                });
            }
        });
        mDialog.show();
        return booleanObservable;
    }
    
    @Override
    public void onSubmitSucceed() {
        Toast.makeText(this, "订单提交成功", Toast.LENGTH_SHORT).show();
    }
    
    @Override
    public void onSubmitFailed() {
        Toast.makeText(this, "订单提交失败", Toast.LENGTH_SHORT).show();
    }
}
