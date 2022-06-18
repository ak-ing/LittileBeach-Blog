package com.example.littlebeachblog.data.config;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.littlebeachblog.app.utils.UILoader;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by AK on 2022/2/3 21:35.
 * God bless my code!
 */
public class ApiResponse<T> implements IApiResponse<T> {
    private UILoader.UIStatus loadState = UILoader.UIStatus.NONE;
    private int code;
    @SerializedName("message")
    private String msg;
    private boolean success;
    private String token;
    private T data;
    public T songs;

    public ApiResponse(UILoader.UIStatus loadState) {
        this.loadState = loadState;
        if (loadState == UILoader.UIStatus.NETWORK_ERROR) {
            this.msg = "网路错误.";
            this.success = false;
        }
    }

    public ApiResponse(UILoader.UIStatus loadState, T data) {
        this.loadState = loadState;
        this.data = data;
    }

    @Override
    public String toString() {
        return "ApiResponse{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", success=" + success +
                ", data=" + data +
                '}';
    }

    public UILoader.UIStatus getLoadState() {
        return loadState;
    }

    public ApiResponse<T> setLoadState(UILoader.UIStatus loadState) {
        this.loadState = loadState;
        return this;
    }

    @Override
    public int getCode() {
        return code;
    }

    @NonNull
    @Override
    public String getMessage() {
        return msg;
    }

    @Override
    public boolean isSuccess() {
        return success;
    }

    @NonNull
    @Override
    public String getToken() {
        return token;
    }

    @Override
    public T getData() {
        return data;
    }


    @Nullable
    @Override
    public ApiResponse<T> getOrNull() {
        if (loadState == UILoader.UIStatus.NETWORK_ERROR) return null;
        else return this;
    }

    @Override
    public boolean isNullOrEmpty() {
        if (data == null) return true;
        if (data instanceof List) {
            if (((List) data).isEmpty()) return true;
        }
        return false;
    }

    public interface Result<T> {
        void onResult(ApiResponse<T> dataResult);
    }
}
