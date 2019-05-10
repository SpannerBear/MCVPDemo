package cn.spannerbear.cmvpdemo.mcvp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import cn.spannerbear.cmvpdemo.CallBack;
import cn.spannerbear.cmvpdemo.InquiryDialog;
import cn.spannerbear.cmvpdemo.R;
import cn.spannerbear.cmvpdemo.SubmitOrderEntity;

public class SubmitOrderActivity extends AppCompatActivity implements SubmitOrderContract.IView {
    
    
    private SubmitOrderPresenter mSubmitOrderPresenter;
    
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
    public void askUserForUpgrade(final CallBack<Boolean> callback) {
        final InquiryDialog dialog = new InquiryDialog(this);
        dialog.setOnResultListener(new InquiryDialog.OnResultListener() {
            @Override
            public void onResult(boolean result) {
                dialog.dismiss();
                callback.onResult(result);
            }
        });
        dialog.show();
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
