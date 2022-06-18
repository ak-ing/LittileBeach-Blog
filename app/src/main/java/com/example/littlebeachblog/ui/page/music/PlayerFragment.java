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

package com.example.littlebeachblog.ui.page.music;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.ViewDataBinding;

import com.example.littlebeachblog.BR;
import com.example.littlebeachblog.R;
import com.example.littlebeachblog.app.BaseFragment;
import com.example.littlebeachblog.app.DataBindingConfig;
import com.example.littlebeachblog.app.utils.ConstantsKt;
import com.example.littlebeachblog.databinding.FragmentPlayerBinding;
import com.example.littlebeachblog.domain.message.SharedViewModel;
import com.example.littlebeachblog.player.PlayerManager;
import com.example.littlebeachblog.player.PlayingInfoManager;
import com.example.littlebeachblog.ui.helper.DefaultInterface;
import com.example.littlebeachblog.ui.state.PlayerViewModel;
import com.example.littlebeachblog.ui.view.player.PlayerSlideListener;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import net.steamcrafted.materialiconlib.MaterialDrawableBuilder;

import eightbitlab.com.blurview.RenderScriptBlur;

public class PlayerFragment extends BaseFragment {

    private PlayerViewModel mState;
    private SharedViewModel mEvent;

    @Override
    protected void initViewModel() {
        mState = getFragmentScopeViewModel(PlayerViewModel.class);
        mEvent = getApplicationScopeViewModel(SharedViewModel.class);
    }

    @Override
    protected DataBindingConfig getDataBindingConfig() {
        return new DataBindingConfig(R.layout.fragment_player, BR.vm, mState)
                .addBindingParam(BR.click, new ClickProxy())
                .addBindingParam(BR.listener, new ListenerHandler());
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mEvent.getToAddSlideListener().observe(getViewLifecycleOwner(), aBoolean -> {
            if (!aBoolean) return;
            if (view.getParent().getParent() instanceof SlidingUpPanelLayout) {
                SlidingUpPanelLayout sliding = (SlidingUpPanelLayout) view.getParent().getParent();
                ViewDataBinding binding = getBinding();
                PlayerSlideListener slideListener = new PlayerSlideListener((FragmentPlayerBinding) binding, sliding);
                sliding.addPanelSlideListener(slideListener);
                PlayerManager.getInstance().getChangeMusicEvent().observe(getViewLifecycleOwner(), changeMusic -> {
                    mState.title.set(changeMusic.getTitle());
                    mState.artist.set(changeMusic.getArtist().getName());
                    mState.coverImg.set(changeMusic.getImg());
                    if (sliding.getPanelState() == SlidingUpPanelLayout.PanelState.COLLAPSED) {  //是折叠状态加载的
                        slideListener.isCollapsedImg.setValue(changeMusic.getImg());
                    } else {
                        slideListener.isCollapsedImg.setValue("");
                    }
                    getBinding().executePendingBindings();
                    slideListener.calculateTitleAndArtist();
                });
            }
        });


        PlayerManager.getInstance().getPlayingMusicEvent().observe(getViewLifecycleOwner(), playingMusic -> {
            mState.maxSeekDuration.set(playingMusic.getDuration());
            mState.currentSeekPosition.set(playingMusic.getPlayerPosition());
        });

        PlayerManager.getInstance().getPauseEvent().observe(getViewLifecycleOwner(), aBoolean -> {
            mState.isPlaying.set(!aBoolean);
        });

        PlayerManager.getInstance().getPlayModeEvent().observe(getViewLifecycleOwner(), anEnum -> {
            int tip;
            if (anEnum == PlayingInfoManager.RepeatMode.LIST_CYCLE) {
                mState.playModeIcon.set(MaterialDrawableBuilder.IconValue.REPEAT);
                tip = R.string.play_repeat;
            } else if (anEnum == PlayingInfoManager.RepeatMode.SINGLE_CYCLE) {
                mState.playModeIcon.set(MaterialDrawableBuilder.IconValue.REPEAT_ONCE);
                tip = R.string.play_repeat_once;
            } else {
                mState.playModeIcon.set(MaterialDrawableBuilder.IconValue.SHUFFLE);
                tip = R.string.play_shuffle;
            }
            if (view.getParent().getParent() instanceof SlidingUpPanelLayout) {
                SlidingUpPanelLayout sliding = (SlidingUpPanelLayout) view.getParent().getParent();
                if (sliding.getPanelState() == SlidingUpPanelLayout.PanelState.EXPANDED) {
                    Toast.makeText(mActivity.getApplicationContext(), getString(tip), Toast.LENGTH_SHORT).show();
                }
            }
        });

        mEvent.getToCloseSlidePanelIfExpanded().observe(this, aBoolean -> {

            if (view.getParent().getParent() instanceof SlidingUpPanelLayout) {

                SlidingUpPanelLayout sliding = (SlidingUpPanelLayout) view.getParent().getParent();

                if (sliding.getPanelState() == SlidingUpPanelLayout.PanelState.EXPANDED) {
                    sliding.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
                } else {
                    mEvent.getToCloseActivityIfAllowed().setValue(true);
                }
            } else {
                mEvent.getToCloseActivityIfAllowed().setValue(true);
            }
        });

    }

    public class ClickProxy {

        public void playMode() {
            PlayerManager.getInstance().changeMode();
        }

        public void previous() {
            if (PlayerManager.getInstance().getAlbum() != null) {
                PlayerManager.getInstance().playPrevious();
            }
        }

        public void togglePlay() {
            PlayerManager.getInstance().togglePlay();
        }

        public void next() {
            if (PlayerManager.getInstance().getAlbum() != null) {
                PlayerManager.getInstance().playNext();
            }
        }

        public void showPlayList() {
            Toast.makeText(mActivity.getApplicationContext(), getString(R.string.unfinished), Toast.LENGTH_SHORT).show();
        }

        public void slideDown() {
            mEvent.getToCloseSlidePanelIfExpanded().setValue(true);
        }

        public void more() {
        }
    }

    public static class ListenerHandler implements DefaultInterface.OnSeekBarChangeListener {

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {
            PlayerManager.getInstance().setSeek(seekBar.getProgress());
        }
    }

}
