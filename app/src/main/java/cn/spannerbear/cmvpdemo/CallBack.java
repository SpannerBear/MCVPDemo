package cn.spannerbear.cmvpdemo;

/**
 * Created by SpannerBear on 2019/5/8.
 * use to:CMVP
 */
public interface CallBack<Params> {
    void onResult(Params p);
}
