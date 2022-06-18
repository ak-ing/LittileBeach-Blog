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

package com.example.littlebeachblog.ui.view.player;

import android.animation.ArgbEvaluator;
import android.animation.FloatEvaluator;
import android.animation.IntEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.lifecycle.MutableLiveData;

import com.bumptech.glide.Glide;
import com.example.littlebeachblog.R;
import com.example.littlebeachblog.app.utils.ScreenUtils;
import com.example.littlebeachblog.app.utils.TopLevelKt;
import com.example.littlebeachblog.databinding.FragmentPlayerBinding;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import kotlin.Unit;

/**
 * Create by KunMinX at 19/10/29
 */
public class PlayerSlideListener implements SlidingUpPanelLayout.PanelSlideListener {

    private final FragmentPlayerBinding mBinding;
    private final SlidingUpPanelLayout mSlidingUpPanelLayout;

    private int titleEndTranslationX;
    private int artistEndTranslationX;
    private int artistNormalEndTranslationY;
    private int contentNormalEndTranslationY;

    private int modeStartX;
    private int previousStartX;
    private int playPauseStartX;
    private int nextStartX;
    private int playQueueStartX;
    private int playPauseEndX;
    private int previousEndX;
    private int modeEndX;
    private int nextEndX;
    private int playQueueEndX;
    private int iconContainerStartY;
    private int iconContainerEndY;

    private final int screenWidth;
    private final int screenHeight;

    private final IntEvaluator intEvaluator = new IntEvaluator();
    private final FloatEvaluator floatEvaluator = new FloatEvaluator();
    private final ArgbEvaluator colorEvaluator = new ArgbEvaluator();

    private final int nowPlayingCardColor;
    private final int playPauseDrawableColor;
    private Status mStatus = Status.COLLAPSED;

    public final int ANIMATION_DURATION = 200;
    private ValueAnimator mSeekGroupShowAnim;
    private ValueAnimator mSeekGroupHideAnim;

    public final MutableLiveData<String> isCollapsedImg = new MutableLiveData<>();


    public enum Status {
        EXPANDED,
        COLLAPSED,
        FULLSCREEN
    }

    public PlayerSlideListener(FragmentPlayerBinding binding, SlidingUpPanelLayout slidingUpPanelLayout) {
        mBinding = binding;
        mSlidingUpPanelLayout = slidingUpPanelLayout;
        screenWidth = ScreenUtils.getScreenWidth();
        screenHeight = slidingUpPanelLayout.getHeight();
        playPauseDrawableColor = Color.BLACK;
        nowPlayingCardColor = Color.WHITE;
        calculateTitleAndArtist();
        calculateIcons();
        //进度条显示隐藏过渡
        mBinding.playPause.setDrawableColor(playPauseDrawableColor);
        mSeekGroupShowAnim = ValueAnimator.ofFloat(0f, 1.0f);
        mSeekGroupShowAnim.setDuration(ANIMATION_DURATION);
        mSeekGroupShowAnim.addUpdateListener(animation -> {
            mBinding.seekGroup.setAlpha((Float) animation.getAnimatedValue());
        });
        mSeekGroupHideAnim = ValueAnimator.ofFloat(1.0f, 0f);
        mSeekGroupHideAnim.setDuration(ANIMATION_DURATION);
        mSeekGroupHideAnim.addUpdateListener(animation -> {
            mBinding.seekGroup.setAlpha((Float) animation.getAnimatedValue());
        });
    }

    @Override
    public void onPanelSlide(View panel, float slideOffset) {
        CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) mBinding.albumArt.getLayoutParams();

        //animate albumImage
        int tempImgSize = intEvaluator.evaluate(slideOffset, TopLevelKt.dpToPx(45), screenWidth);
        params.width = tempImgSize;
        params.height = tempImgSize;
        mBinding.albumArt.setLayoutParams(params);

        //animate title and artist
        mBinding.title.setTranslationX(floatEvaluator.evaluate(slideOffset, 0, titleEndTranslationX));
        mBinding.artist.setTranslationX(floatEvaluator.evaluate(slideOffset, 0, artistEndTranslationX));
        mBinding.artist.setTranslationY(floatEvaluator.evaluate(slideOffset, 0, artistNormalEndTranslationY));
        mBinding.summary.setTranslationY(floatEvaluator.evaluate(slideOffset, 0, contentNormalEndTranslationY));

        //aniamte icons
        mBinding.playPause.setX(intEvaluator.evaluate(slideOffset, playPauseStartX, playPauseEndX));
        mBinding.playPause.setCircleAlpha(intEvaluator.evaluate(slideOffset, 0, 255));
        mBinding.playPause.setDrawableColor((int) colorEvaluator.evaluate(slideOffset, playPauseDrawableColor, nowPlayingCardColor));
        mBinding.previous.setX(intEvaluator.evaluate(slideOffset, previousStartX, previousEndX));
        mBinding.mode.setX(intEvaluator.evaluate(slideOffset, modeStartX, modeEndX));
        mBinding.next.setX(intEvaluator.evaluate(slideOffset, nextStartX, nextEndX));
        mBinding.icPlayList.setX(intEvaluator.evaluate(slideOffset, playQueueStartX, playQueueEndX));
        mBinding.mode.setAlpha(floatEvaluator.evaluate(slideOffset, 0, 1));
//    mBinding.previous.setAlpha(floatEvaluator.evaluate(slideOffset, 0, 1));
        mBinding.iconContainer.setY(intEvaluator.evaluate(slideOffset, iconContainerStartY, iconContainerEndY));

        CoordinatorLayout.LayoutParams params1 = (CoordinatorLayout.LayoutParams) mBinding.summary.getLayoutParams();
        params1.height = intEvaluator.evaluate(slideOffset, TopLevelKt.dpToPx(45), TopLevelKt.dpToPx(60));
        mBinding.summary.setLayoutParams(params1);

        //seek
        mBinding.seekGroup.setY(iconContainerEndY - TopLevelKt.dpToPx(46));
    }

    @Override
    public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState previousState,
                                    SlidingUpPanelLayout.PanelState newState) {
        if (previousState == SlidingUpPanelLayout.PanelState.COLLAPSED) {
            if (mBinding.songProgressNormal.getVisibility() != View.INVISIBLE) {
                mBinding.songProgressNormal.setVisibility(View.INVISIBLE);
            }
            if (mBinding.mode.getVisibility() != View.VISIBLE) {
                mBinding.mode.setVisibility(View.VISIBLE);
            }
            if (mBinding.previous.getVisibility() != View.VISIBLE) {
                mBinding.previous.setVisibility(View.VISIBLE);
            }
//            if (mBinding.seekGroup.getVisibility() != View.INVISIBLE) {
//                mBinding.seekGroup.setVisibility(View.INVISIBLE);
//            }
        }

        if (newState == SlidingUpPanelLayout.PanelState.EXPANDED) {
            String value = isCollapsedImg.getValue();
            if (!TextUtils.isEmpty(value)) {
                Glide.with(mBinding.getRoot()).load(value).placeholder(mBinding.albumArt.getDrawable()).into(mBinding.albumArt);
            }

            mSeekGroupShowAnim.start();

            mStatus = Status.EXPANDED;
            toolbarSlideIn(panel.getContext());
            mBinding.mode.setClickable(true);
            mBinding.previous.setClickable(true);
        } else if (newState == SlidingUpPanelLayout.PanelState.COLLAPSED) {
            mStatus = Status.COLLAPSED;
            if (mBinding.songProgressNormal.getVisibility() != View.VISIBLE) {
                mBinding.songProgressNormal.setVisibility(View.VISIBLE);
            }
            if (mBinding.mode.getVisibility() != View.GONE) {
                mBinding.mode.setVisibility(View.GONE);
            }

            mBinding.topContainer.setOnClickListener(v -> {
                if (mSlidingUpPanelLayout.isTouchEnabled()) {
                    mSlidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
                }
            });
        } else if (newState == SlidingUpPanelLayout.PanelState.DRAGGING) {
            if (mBinding.customToolbar.getVisibility() != View.INVISIBLE) {
                mBinding.customToolbar.setVisibility(View.INVISIBLE);
            }
            mSeekGroupHideAnim.start();
        }

    }

    public void calculateTitleAndArtist() {
        Rect titleBounds = new Rect();
        mBinding.title.getPaint().getTextBounds(mBinding.title.getText().toString(), 0,
                mBinding.title.getText().length(), titleBounds);
        int titleWidth = titleBounds.width();

        Rect artistBounds = new Rect();
        mBinding.artist.getPaint().getTextBounds(mBinding.artist.getText().toString(), 0,
                mBinding.artist.getText().length(), artistBounds);
        int artistWidth = artistBounds.width();

        titleEndTranslationX = (screenWidth / 2) - (titleWidth / 2) - TopLevelKt.dpToPx(57);

        artistEndTranslationX = (screenWidth / 2) - (artistWidth / 2) - TopLevelKt.dpToPx(57);
        artistNormalEndTranslationY = TopLevelKt.dpToPx(12);

        contentNormalEndTranslationY = screenWidth + TopLevelKt.dpToPx(32);

        if (mStatus == Status.COLLAPSED) {
            mBinding.title.setTranslationX(0);
            mBinding.artist.setTranslationX(0);
        } else {
            mBinding.title.setTranslationX(titleEndTranslationX);
            mBinding.artist.setTranslationX(artistEndTranslationX);
        }
    }

    private void calculateIcons() {
        modeStartX = mBinding.mode.getLeft();
        previousStartX = mBinding.previous.getLeft();
        playPauseStartX = mBinding.playPause.getLeft();
        nextStartX = mBinding.next.getLeft();
        playQueueStartX = mBinding.icPlayList.getLeft();
        int size = TopLevelKt.dpToPx(36);
        int gap = (screenWidth - 5 * (size)) / 6;
        playPauseEndX = (screenWidth / 2) - (size / 2);
        previousEndX = playPauseEndX - gap - size;
        modeEndX = previousEndX - gap - size;
        nextEndX = playPauseEndX + gap + size;
        playQueueEndX = nextEndX + gap + size;
        iconContainerStartY = mBinding.iconContainer.getTop();
        iconContainerEndY = (int) (screenHeight - 1.5 * mBinding.iconContainer.getHeight() - mBinding.seekBottom.getHeight());
        int padding = TopLevelKt.dpToPx(8) + modeEndX;
        mBinding.seekGroup.setPadding(padding, 0, padding, 0);
    }

    private void toolbarSlideIn(Context context) {
        mBinding.customToolbar.post(() -> {
            Animation animation = AnimationUtils.loadAnimation(context, R.anim.toolbar_slide_in);
            TopLevelKt.onAnimationStart(animation, animation1 -> {
                mBinding.customToolbar.setVisibility(View.VISIBLE);
                return Unit.INSTANCE;
            });

            mBinding.customToolbar.startAnimation(animation);
        });
    }

}
