package com.andevindo.andevindonetworking;

import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.entity.mime.MultipartEntityBuilder;
import cz.msebera.android.httpclient.entity.mime.content.FileBody;

/**
 * Created by -H- on 2/20/2016.
 */
public class VolleyModel<T extends NetworkModel> {

    private String mUrl = Network.getAPIAddress();
    private Map<String, String> mParameter;
    private Map<String, String> mHeaders;
    private HttpEntity mHttpEntity;
    private NetworkMethod mNetworkMethod;
    private MultipartEntityBuilder mMultipartEntityBuilder;
    private boolean isUsingHeader;
    private String mExtraUrl;

    private VolleyModel(ParameterBuilder parameterBuilder) {
        mUrl += parameterBuilder.mUrl;
        mExtraUrl = parameterBuilder.mUrl;
        mParameter = parameterBuilder.mParameter;
        mHeaders = parameterBuilder.mHeaders;
        mNetworkMethod = parameterBuilder.mNetworkMethod;
        isUsingHeader = parameterBuilder.isUsingHeader;
    }

    private VolleyModel(MultiPartEntityBuilder multiPartEntityBuilder) {
        mUrl += multiPartEntityBuilder.mUrl;
        mExtraUrl = multiPartEntityBuilder.mUrl;
        mHttpEntity = multiPartEntityBuilder.mHttpEntity;
        mMultipartEntityBuilder = multiPartEntityBuilder.mMultipartEntityBuilder;
        mHeaders = multiPartEntityBuilder.mHeaders;
        mNetworkMethod = multiPartEntityBuilder.mNetworkMethod;
        isUsingHeader = multiPartEntityBuilder.isUsingHeader;
    }

    public boolean isUsingHeader() {
        return isUsingHeader;
    }

    public void addString(String key, String value) {
        mMultipartEntityBuilder.addTextBody(key, value);
        mHttpEntity = mMultipartEntityBuilder.build();
    }

    public void setCustomBaseURL(String baseURL) {
        mUrl = baseURL;
        mUrl += mExtraUrl;
    }

    public NetworkMethod getNetworkMethod() {
        return mNetworkMethod;
    }

    public void setNetworkMethod(NetworkMethod networkMethod) {
        mNetworkMethod = networkMethod;
    }

    public String getUrl() {
        return mUrl;
    }

    public HttpEntity getHttpEntity() {
        return mHttpEntity;
    }

    public Map<String, String> getParameter() {
        return mParameter;
    }

    public void setParameter(Map<String, String> parameter) {
        mParameter = parameter;
        mMultipartEntityBuilder = null;
    }

    public void setParameter(MultiPartEntityBuilder multiPartEntityBuilder) {
        multiPartEntityBuilder = multiPartEntityBuilder;
        mParameter = null;
    }

    public void addParameter(String key, String value) {
        if (mMultipartEntityBuilder!=null){
            mMultipartEntityBuilder.addTextBody(key, value);
            mHttpEntity = mMultipartEntityBuilder.build();
        }else{
            mParameter.put(key, value);
        }
    }

    public void addParameter(String key, int value) {
       if (mMultipartEntityBuilder!=null){
            mMultipartEntityBuilder.addTextBody(key, value + "");
            mHttpEntity = mMultipartEntityBuilder.build();
        }else{
            mParameter.put(key, value + "");
        }
    }

    public void addParameter(String key, float value) {
        if (mMultipartEntityBuilder!=null){
            mMultipartEntityBuilder.addTextBody(key, value + "");
            mHttpEntity = mMultipartEntityBuilder.build();
        }else{
            mParameter.put(key, value + "");
        }
    }

    public void addParameter(String key, double value) {
        if (mMultipartEntityBuilder!=null){
            mMultipartEntityBuilder.addTextBody(key, value + "");
            mHttpEntity = mMultipartEntityBuilder.build();
        }else{
            mParameter.put(key, value + "");
        }
    }

    public void addParameter(String key, boolean value) {
        if (mMultipartEntityBuilder!=null){
            mMultipartEntityBuilder.addTextBody(key, value + "");
            mHttpEntity = mMultipartEntityBuilder.build();
        }else{
            mParameter.put(key, value + "");
        }
    }

    public void addParameter(String key, File value) {
        if (mMultipartEntityBuilder!=null){
            mMultipartEntityBuilder.addTextBody(key, value + "");
            mHttpEntity = mMultipartEntityBuilder.build();
        }
    }

    public void setHeaders(Map<String, String> headers) {
        mHeaders = headers;
    }

    public Map<String, String> getHeaders() {
        return mHeaders;
    }

    public static class ParameterBuilder {
        private Map<String, String> mParameter;
        private Map<String, String> mHeaders;
        private String mUrl;
        private NetworkMethod mNetworkMethod = NetworkMethod.GET;
        private boolean isUsingHeader;
        //private int[] mResponseCodes = new int[1];

        public ParameterBuilder(String url) {
            mUrl = url;
        }

        public ParameterBuilder setNetworkMethod(NetworkMethod networkMethod) {
            mNetworkMethod = networkMethod;
            return this;
        }

        public ParameterBuilder addParameter(String key, String value) {
            if (mParameter == null)
                mParameter = new HashMap<>();
            mParameter.put(key, value);
            return this;
        }

        public ParameterBuilder addParameter(String key, int value) {
            if (mParameter == null)
                mParameter = new HashMap<>();
            mParameter.put(key, value + "");
            return this;
        }

        public ParameterBuilder addParameter(String key, float value) {
            if (mParameter == null)
                mParameter = new HashMap<>();
            mParameter.put(key, value + "");
            return this;
        }

        public ParameterBuilder addParameter(String key, double value) {
            if (mParameter == null)
                mParameter = new HashMap<>();
            mParameter.put(key, value + "");
            return this;
        }

        public ParameterBuilder addParameter(String key, boolean value) {
            if (mParameter == null)
                mParameter = new HashMap<>();
            mParameter.put(key, value + "");
            return this;
        }

        public ParameterBuilder setUsingHeader(boolean usingHeader) {
            isUsingHeader = usingHeader;
            return this;
        }

        public ParameterBuilder setHeaders(Map<String, String> headers) {
            mHeaders = headers;
            return this;
        }

        public VolleyModel build() {
            return new VolleyModel(this);
        }
    }

    public static class MultiPartEntityBuilder {
        private MultipartEntityBuilder mMultipartEntityBuilder;
        private HttpEntity mHttpEntity;
        private Map<String, String> mHeaders;
        private String mUrl;
        private NetworkMethod mNetworkMethod = NetworkMethod.POST;
        private boolean isUsingHeader;

        public MultiPartEntityBuilder(String url) {
            mUrl = url;
            mMultipartEntityBuilder = MultipartEntityBuilder.create();
        }

        public MultiPartEntityBuilder addParameter(String key, File file) {
            if (file!=null){
                FileBody fileBody = new FileBody(file);
                mMultipartEntityBuilder.addPart(key, fileBody);
            }else{
                mMultipartEntityBuilder.addPart(key, null);
            }
            return this;
        }

        public MultiPartEntityBuilder addParameter(String key, String value) {
            mMultipartEntityBuilder.addTextBody(key, value);
            return this;
        }

        public MultiPartEntityBuilder addParameter(String key, int value) {
            mMultipartEntityBuilder.addTextBody(key, value + "");
            return this;
        }

        public MultiPartEntityBuilder addParameter(String key, boolean value) {
            mMultipartEntityBuilder.addTextBody(key, value + "");
            return this;
        }

        public MultiPartEntityBuilder addParameter(String key, float value) {
            mMultipartEntityBuilder.addTextBody(key, value + "");
            return this;
        }

        public MultiPartEntityBuilder addParameter(String key, double value) {
            mMultipartEntityBuilder.addTextBody(key, value + "");
            return this;
        }

        public MultiPartEntityBuilder setUsingHeader(boolean usingHeader) {
            isUsingHeader = usingHeader;
            return this;
        }

        public MultiPartEntityBuilder setHeaders(Map<String, String> headers) {
            mHeaders = headers;
            return this;
        }

        public VolleyModel build() {
            mHttpEntity = mMultipartEntityBuilder.build();
            return new VolleyModel(this);
        }
    }

}
