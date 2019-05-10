package cn.spannerbear.cmvpdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import cn.spannerbear.cmvpdemo.normal.SubmitOrderActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    
    private Button mBtnNormal;
    private Button mBtnMCVP;
    private Button mBtnRxMCVP;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBtnNormal = findViewById(R.id.btn_normal);
        mBtnMCVP = findViewById(R.id.btn_mcvp);
        mBtnRxMCVP = findViewById(R.id.btn_rxmcvp);
        mBtnNormal.setOnClickListener(this);
        mBtnMCVP.setOnClickListener(this);
        mBtnRxMCVP.setOnClickListener(this);
    }
    
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_normal:
                Intent intent = new Intent(this, SubmitOrderActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_mcvp:
                Intent intent2 = new Intent(this, cn.spannerbear.cmvpdemo.mcvp.SubmitOrderActivity.class);
                startActivity(intent2);
                break;
            case R.id.btn_rxmcvp:
                Intent intent3 = new Intent(this, cn.spannerbear.cmvpdemo.rxmcvp.SubmitOrderActivity.class);
                startActivity(intent3);
                break;
        }
    }
}
