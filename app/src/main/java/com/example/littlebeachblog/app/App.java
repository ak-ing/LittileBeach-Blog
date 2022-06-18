/*
 *
 *  * Copyright 2018-present KunMinX
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *    http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */

package com.example.littlebeachblog.app;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelStore;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.room.Room;

import com.example.littlebeachblog.R;
import com.example.littlebeachblog.app.utils.ConstantsKt;
import com.example.littlebeachblog.data.room.AppDataBase;
import com.example.littlebeachblog.data.room.PlayHistoryEntity;
import com.example.littlebeachblog.player.PlayerManager;
import com.example.littlebeachblog.player.contract.IMusicPlayCallBack;
import com.scwang.smart.refresh.header.MaterialHeader;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.DefaultRefreshInitializer;
import com.tencent.bugly.Bugly;
import com.tencent.bugly.crashreport.CrashReport;

import net.mikaelzero.mojito.Mojito;
import net.mikaelzero.mojito.loader.glide.GlideImageLoader;
import net.mikaelzero.mojito.view.sketch.SketchImageLoadFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import kotlinx.coroutines.Dispatchers;


/**
 * Create by KunMinX at 2020/7/4
 */
public class App extends Application implements ViewModelStoreOwner {

    private static Handler sHandler = null;
    private static App mApp;
    private final ExecutorService executor = Executors.newCachedThreadPool();
    private ViewModelStore mAppViewModelStore;
    public final Map<String, String> mMap = new HashMap<>();

    @Override
    public void onCreate() {
        super.onCreate();
        mApp = this;
        Bugly.init(getApplicationContext(), "7728baba74", true);
        sHandler = new Handler(Looper.getMainLooper());
        mAppViewModelStore = new ViewModelStore();
        PlayerManager.getInstance().init(this);
        Mojito.initialize(GlideImageLoader.Companion.with(this), new SketchImageLoadFactory());
    }

    @NonNull
    @Override
    public ViewModelStore getViewModelStore() {
        return mAppViewModelStore;
    }

    public static App getApp() {
        return mApp;
    }

    public static Handler getHandler() {
        return sHandler;
    }

    public ExecutorService getExecutor() {
        return executor;
    }

    //static 代码段可以防止内存泄露
    static {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshInitializer((context, layout) -> layout.setDragRate(1));

        SmartRefreshLayout.setDefaultRefreshHeaderCreator((context, layout) -> {
            layout.setPrimaryColorsId(R.color.blue, android.R.color.white);//全局设置主题颜色
            return new MaterialHeader(context);//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
        });
    }
}
