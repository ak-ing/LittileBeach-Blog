/*
 * Copyright 2018-present KunMinX
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.littlebeachblog.ui.binding_adapter;


import static com.example.littlebeachblog.app.utils.ConstantsKt.BASE_URL;

import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.littlebeachblog.R;
import com.example.littlebeachblog.app.utils.ClickUtils;
import com.example.littlebeachblog.app.utils.TopLevelKt;

import org.json.JSONArray;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Create by KunMinX at 19/9/18
 */
public class CommonBindingAdapter {

    @BindingAdapter(value = {"dateTime"}, requireAll = false)
    public static void dateToTime(TextView textView, long date) {
        textView.setText(new SimpleDateFormat("M-d h:mm", Locale.getDefault()).format(date));
    }

    @BindingAdapter(value = {"date"}, requireAll = false)
    public static void dateToDate(TextView textView, long date) {
        textView.setText(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(date));
    }

    @BindingAdapter(value = {"dateToYM"}, requireAll = false)
    public static void dateToYM(TextView textView, long date) {
        textView.setText(new SimpleDateFormat("yyyy年M月注册", Locale.getDefault()).format(date));
    }


    @BindingAdapter(value = {"avatarUrl", "placeHolder"}, requireAll = false)
    public static void loadUrl(ImageView view, String url, Drawable placeHolder) {
        if (url == null) return;
        String imgUrl = BASE_URL + url;
        Glide.with(view.getContext()).load(imgUrl).error(R.drawable.default_avatar)
                .placeholder(R.drawable.default_avatar)
                .into(view);
    }

    @BindingAdapter(value = {"imageUrlList", "imageIndex"})
    public static void loadUrls(ImageView view, String urls, int index) {
        if (urls == null) {
            view.setVisibility(View.GONE);
            return;
        } else {
            view.setVisibility(View.VISIBLE);
        }

        try {
            JSONArray jsonArray = new JSONArray(urls);

            String[] split = urls.split(",");
            for (String s : split) {

            }

            if (jsonArray.length() > index) {
                String imgUrl = BASE_URL + jsonArray.getString(index);

                Glide.with(view.getContext()).load(imgUrl)
                        .placeholder(R.mipmap.picture_icon_placeholder)
                        .listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                view.post(() -> {
                                    view.setImageResource(R.mipmap.picture_icon_data_error);
                                });
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable drawable, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                int width = drawable.getIntrinsicWidth();
                                int height = drawable.getIntrinsicHeight();
                                view.post(() -> {
                                    ViewGroup.LayoutParams lp = view.getLayoutParams();
                                    if (width < height) {
                                        lp.height = TopLevelKt.dp2px(view.getContext(), 180);
                                        lp.width = TopLevelKt.dp2px(view.getContext(), 180 * (height / width));
                                    } else {
                                        lp.width = TopLevelKt.dp2px(view.getContext(), 270);
                                        lp.height = TopLevelKt.dp2px(view.getContext(), 270 * (height / width));
                                    }
                                    view.setLayoutParams(lp);
                                    view.setImageDrawable(drawable);
                                });
                                return false;
                            }
                        }).submit();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @BindingAdapter(value = {"visible"}, requireAll = false)
    public static void visible(View view, boolean visible) {
        view.setVisibility(visible ? View.VISIBLE : View.GONE);
    }

    @BindingAdapter(value = {"showDrawable", "drawableShowed"}, requireAll = false)
    public static void showDrawable(ImageView view, boolean showDrawable, int drawableShowed) {
        view.setImageResource(showDrawable ? drawableShowed : android.R.color.transparent);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @BindingAdapter(value = {"textColorRes"}, requireAll = false)
    public static void setTextColor(TextView textView, int textColorRes) {
        textView.setTextColor(textView.getContext().getColor(textColorRes));
    }

    @BindingAdapter(value = {"imageUrl", "placeHolder", "errorDrawable"}, requireAll = false)
    public static void loadImgUrl(ImageView view, String url, Drawable placeHolder, Drawable error) {
        Glide.with(view.getContext()).load(url)
                .placeholder(placeHolder)
                .error(error)
                .into(view);
    }

    @BindingAdapter(value = {"imageRes"}, requireAll = false)
    public static void setImageRes(ImageView imageView, int imageRes) {
        imageView.setImageResource(imageRes);
    }

    @BindingAdapter(value = {"selected"}, requireAll = false)
    public static void selected(View view, boolean select) {
        view.setSelected(select);
    }


    @BindingAdapter(value = {"onClickWithDebouncing"}, requireAll = false)
    public static void onClickWithDebouncing(View view, View.OnClickListener clickListener) {
        ClickUtils.applySingleDebouncing(view, clickListener);
    }

    @BindingAdapter(value = {"adjustWidth"})
    public static void adjustWidth(View view, int adjustWidth) {
        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.width = adjustWidth;
        view.setLayoutParams(params);
    }

    @BindingAdapter(value = {"adjustHeight"})
    public static void adjustHeight(View view, int adjustHeight) {
        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.height = adjustHeight;
        view.setLayoutParams(params);
    }
}
