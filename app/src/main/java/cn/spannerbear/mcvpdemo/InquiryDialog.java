package cn.spannerbear.mcvpdemo;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

/**
 * Created by SpannerBear on 2019/5/8.
 * use to:
 */
public class InquiryDialog extends Dialog {
    
    private OnResultListener mListener;
    
    public InquiryDialog(Context context) {
        super(context);
    }
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View inflate = LayoutInflater.from(getContext()).inflate(R.layout.dialog_inquiry, null);
        setContentView(inflate);
        Button btnYes = inflate.findViewById(R.id.btn_yes);
        Button btnNo = inflate.findViewById(R.id.btn_no);
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onResult(true);
                }
            }
        });
        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onResult(false);
                }
            }
        });
    }
    
    public void setOnResultListener(OnResultListener listener) {
        mListener = listener;
    }
    
    public interface OnResultListener{
        void onResult(boolean result);
    }
}
