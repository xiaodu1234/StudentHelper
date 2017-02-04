package Result;

import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import NetWork.BaseCallback;

/**
 * Created by duchaoqiang on 2017/1/9.
 */
public class StringCallBack<T> extends BaseCallback<T> {
    @Override
    public void onRequestBefore() {

    }

    @Override
    public void onFailure(Request request, Exception e) {

    }

    @Override
    public void onSuccess(Response response, T t) {

    }

    @Override
    public void onError(Response response, int errorCode, Exception e) {

    }
}
