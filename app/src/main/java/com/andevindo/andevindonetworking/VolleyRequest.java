package com.andevindo.andevindonetworking;

import android.content.Context;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by -Andevindo- on 10/8/2015.
 */
public class VolleyRequest {
    private RequestQueue mRequestQueue;
    private CustomRequest mCustomRequest;
    private Context mContext;

    public VolleyRequest(Context context) {
        mContext = context;
        mRequestQueue = VolleySingleton.getInstance(context).getRequestQueue();
    }


    private boolean checkCode(JSONObject jsonObject, int codePattern) throws JSONException {
        int code = 0;
        code = jsonObject.getInt("kode");
        Log.d("kode", code + "");
        if (code == codePattern)
            return true;
        else
            return false;
    }

    public void sendRequest(final String tag, final VolleyModel volleyModel,
                            final VolleyListener.VolleySuccessListener successListener,
                            final VolleyListener.VolleyErrorListener volleyErrorListener,
                            final VolleyListener.VolleyErrorGlobalListener globalListener,
                            NetworkConfiguration networkConfiguration){
        int method;
        if (volleyModel.getNetworkMethod()==NetworkMethod.POST)
            method = Request.Method.POST;
        else if (volleyModel.getNetworkMethod()==NetworkMethod.GET)
            method = Request.Method.GET;
        else if (volleyModel.getNetworkMethod()==NetworkMethod.PUT)
            method = Request.Method.PUT;
        else
            method = Request.Method.DELETE;
        mCustomRequest = new CustomRequest(mContext, method, volleyModel.getUrl(), volleyModel.getHeaders(),
                volleyModel.getParameter(), volleyModel.getHttpEntity(), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (checkCode(response, Network.getSuccessCode())) {

                        successListener.onSuccess(response, tag);

                    } else {
                        successListener.onOtherResponse(response, tag);
                    }
                } catch (JSONException e) {
                    Log.d("VolleyResponse", tag + ":" + "Parse Error");
                    volleyErrorListener.onParseError();
                    globalListener.onErrorGlobalListener(tag);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (VolleyErrorMessage.isConnectionProblem(error)) {
                    Log.d("VolleyResponse", tag + ":" + "Network Error");
                    volleyErrorListener.onNetworkError(tag);
                } else {
                    Log.d("VolleyResponse", tag + ":" + "Server Error");
                    volleyErrorListener.onServerError(tag);
                }
                globalListener.onErrorGlobalListener(tag);
            }
        });

        mCustomRequest.setRetryPolicy(new DefaultRetryPolicy(networkConfiguration.getSocketTimeOut(),
                networkConfiguration.getRetries(), DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        mCustomRequest.setTag(tag);
        mRequestQueue.add(mCustomRequest);

    }

    public void cancelAllRequest(String tag) {
        if (mCustomRequest != null)
            mRequestQueue.cancelAll(tag);

    }
}
