/*
 * Copyright 2018-2019 KunMinX
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

package com.example.littlebeachblog.player;

import static com.example.littlebeachblog.app.utils.ConstantsKt.roomDir;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;


import com.example.littlebeachblog.app.App;
import com.example.littlebeachblog.app.utils.TopLevelKt;
import com.example.littlebeachblog.data.repository.MusicRepository;
import com.example.littlebeachblog.data.room.AppDataBase;
import com.example.littlebeachblog.data.room.PlayHistoryEntity;
import com.example.littlebeachblog.player.bean.base.BaseAlbumItem;
import com.example.littlebeachblog.player.bean.base.BaseMusicItem;
import com.example.littlebeachblog.player.bean.dto.ChangeMusic;
import com.example.littlebeachblog.player.bean.dto.PlayingMusic;
import com.example.littlebeachblog.player.contract.ICacheProxy;
import com.example.littlebeachblog.player.contract.IMusicPlayCallBack;
import com.example.littlebeachblog.player.contract.IServiceNotifier;
import com.example.littlebeachblog.player.helper.MediaPlayerHelper;

import java.util.List;


/**
 * Create by KunMinX at 18/9/25
 */
public class PlayerController<B extends BaseAlbumItem, M extends BaseMusicItem> {

    private PlayingInfoManager<B, M> mPlayingInfoManager = new PlayingInfoManager<>();
    private boolean mIsPaused;
    private boolean mIsChangingPlayingMusic;

    private ICacheProxy mICacheProxy;       //缓存
    private IMusicPlayCallBack playCallBack;    //播放回调

    private final MutableLiveData<ChangeMusic> changeMusicLiveData = new MutableLiveData<>();
    private final MutableLiveData<PlayingMusic> playingMusicLiveData = new MutableLiveData<>();
    private final MutableLiveData<Boolean> pauseLiveData = new MutableLiveData<>();
    private final MutableLiveData<Enum> playModeLiveData = new MutableLiveData<>();
    private final SharedPreferences sp = TopLevelKt.getSp("app");
    private final SharedPreferences.Editor editor = sp.edit();

    private IServiceNotifier mIServiceNotifier;

    private PlayingMusic mCurrentPlay = new PlayingMusic("00:00", "00:00");
    private ChangeMusic mChangeMusic = new ChangeMusic();

    public void init(Context context, List<String> extraFormatList,
                     IServiceNotifier iServiceNotifier,
                     ICacheProxy iCacheProxy) {

        mIServiceNotifier = iServiceNotifier;
        mICacheProxy = iCacheProxy;

        MediaPlayerHelper.getInstance().initAssetManager(context);

        if (extraFormatList != null) {
            MediaPlayerHelper.getInstance().getFormatList().addAll(extraFormatList);
        }

    }

    public void setPlayCallBack(IMusicPlayCallBack playCallBack) {
        this.playCallBack = playCallBack;
    }

    public boolean isInit() {
        return mPlayingInfoManager.isInit();
    }

    public void loadAlbum(B musicAlbum) {
        setAlbum(musicAlbum, sp.getInt("lastIndex", 0));
    }

    private void setAlbum(B musicAlbum, int albumIndex) {
        mPlayingInfoManager.setMusicAlbum(musicAlbum);
        mPlayingInfoManager.setAlbumIndex(albumIndex);
        setChangingPlayingMusic(true);
    }

    public void loadAlbum(B musicAlbum, int albumIndex) {
        setAlbum(musicAlbum, albumIndex);
        playAudio();
    }

    public boolean isPlaying() {
        return MediaPlayerHelper.getInstance().getMediaPlayer().isPlaying();
    }

    public boolean isPaused() {
        return mIsPaused;
    }

    /**
     * @param albumIndex 从 album 进来的一定是 album 列表的 index
     */
    public void playAudio(int albumIndex) {
        if (isPlaying() && albumIndex == mPlayingInfoManager.getAlbumIndex()) {
            return;
        }

        mPlayingInfoManager.setAlbumIndex(albumIndex);
        setChangingPlayingMusic(true);
        playAudio();
    }


    public void playAudio() {
        if (mIsChangingPlayingMusic) {
            MediaPlayerHelper.getInstance().getMediaPlayer().stop();
            getUrlAndPlay();
            editor.putInt("lastIndex", PlayerManager.getInstance().getAlbumIndex()).apply();
        } else if (mIsPaused) {
            resumeAudio();
        }
    }

    private void getUrlAndPlay() {
        M freeMusic = getCurrentPlayingMusic();

        String url = freeMusic.getUrl();
        if (TextUtils.isEmpty(url)) {
            pauseAudio();
        } else {
            //涉及到网络请求，因而使用时 请在外部自行判断网络连接状态
            if ((url.contains("http:") || url.contains("ftp:") || url.contains("https:"))) {
                MediaPlayerHelper.getInstance().play(mICacheProxy.getCacheUrl(url));
                afterPlay();
            } else if (url.contains("storage")) {
                MediaPlayerHelper.getInstance().play(url);
                afterPlay();
            } else {
                MediaPlayerHelper.getInstance().playAsset(url);
                afterPlay();
            }
        }
    }

    private void afterPlay() {
        setChangingPlayingMusic(false);
        bindProgressListener();
        mIsPaused = false;
        pauseLiveData.postValue(false);
        if (mIServiceNotifier != null) {
            mIServiceNotifier.notifyService(true);
        }
        if (playCallBack != null) {            //添加播放记录
            M music = getCurrentPlayingMusic();
            playCallBack.afterPlayed(music.asRoomEntity());
        }
    }

    private void bindProgressListener() {
        MediaPlayerHelper.getInstance().setProgressInterval(1000).setMediaPlayerHelperCallBack(
                (state, mediaPlayerHelper, args) -> {
                    if (state == MediaPlayerHelper.CallBackState.PROGRESS) {
                        int position = mediaPlayerHelper.getMediaPlayer().getCurrentPosition();
                        int duration = mediaPlayerHelper.getMediaPlayer().getDuration();
                        mCurrentPlay.setNowTime(calculateTime(position / 1000));
                        mCurrentPlay.setAllTime(calculateTime(duration / 1000));
                        mCurrentPlay.setDuration(duration);
                        mCurrentPlay.setPlayerPosition(position);
                        playingMusicLiveData.postValue(mCurrentPlay);
                        if (mCurrentPlay.getAllTime().equals(mCurrentPlay.getNowTime())
                                //容许两秒内的误差，有的内容它就是会差那么 1 秒
                                || duration / 1000 - position / 1000 < 2) {
                            if (getRepeatMode() == PlayingInfoManager.RepeatMode.SINGLE_CYCLE) {
                                playAgain();
                            } else {
                                playNext();
                            }
                        }
                    }
                });
    }

    public void requestLastPlayingInfo() {
        playingMusicLiveData.postValue(mCurrentPlay);
        changeMusicLiveData.postValue(mChangeMusic);
        pauseLiveData.postValue(mIsPaused);
    }

    public void setSeek(int progress) {
        MediaPlayerHelper.getInstance().getMediaPlayer().seekTo(progress);
    }

    public String getTrackTime(int progress) {
        return calculateTime(progress / 1000);
    }

    private String calculateTime(int time) {
        int minute;
        int second;
        if (time >= 60) {
            minute = time / 60;
            second = time % 60;
            return (minute < 10 ? "0" + minute : "" + minute) + (second < 10 ? ":0" + second : ":" + second);
        } else {
            second = time;
            if (second < 10) {
                return "00:0" + second;
            }
            return "00:" + second;
        }
    }


    public void playNext() {
        mPlayingInfoManager.countNextIndex();
        setChangingPlayingMusic(true);
        playAudio();
    }


    public void playPrevious() {
        mPlayingInfoManager.countPreviousIndex();
        setChangingPlayingMusic(true);
        playAudio();
    }


    public void playAgain() {
        setChangingPlayingMusic(true);
        playAudio();
    }


    public void pauseAudio() {
        MediaPlayerHelper.getInstance().getMediaPlayer().pause();
        mIsPaused = true;
        pauseLiveData.postValue(true);
        if (mIServiceNotifier != null) {
            mIServiceNotifier.notifyService(true);
        }
    }


    public void resumeAudio() {
        MediaPlayerHelper.getInstance().getMediaPlayer().start();
        mIsPaused = false;
        pauseLiveData.postValue(false);
        if (mIServiceNotifier != null) {
            mIServiceNotifier.notifyService(true);
        }
    }


    public void clear() {
        MediaPlayerHelper.getInstance().getMediaPlayer().stop();
        MediaPlayerHelper.getInstance().getMediaPlayer().reset();
        pauseLiveData.postValue(true);
        //这里设为true是因为可能通知栏清除后，还可能在页面中点击播放
        resetIsChangingPlayingChapter();
        MediaPlayerHelper.getInstance().setProgressInterval(1000).setMediaPlayerHelperCallBack(null);
        if (mIServiceNotifier != null) {
            mIServiceNotifier.notifyService(false);
        }
    }

    public void resetIsChangingPlayingChapter() {
        mIsChangingPlayingMusic = true;
        setChangingPlayingMusic(true);
    }

    public void changeMode() {
        playModeLiveData.postValue(mPlayingInfoManager.changeMode());
    }

    public B getAlbum() {
        return mPlayingInfoManager.getMusicAlbum();
    }

    //播放列表展示用
    public List<M> getAlbumMusics() {
        return mPlayingInfoManager.getOriginPlayingList();
    }

    public void setChangingPlayingMusic(boolean changingPlayingMusic) {
        mIsChangingPlayingMusic = changingPlayingMusic;
        if (mIsChangingPlayingMusic) {
            mChangeMusic.setBaseInfo(mPlayingInfoManager.getMusicAlbum(), getCurrentPlayingMusic());
            changeMusicLiveData.postValue(mChangeMusic);
            mCurrentPlay.setBaseInfo(mPlayingInfoManager.getMusicAlbum(), getCurrentPlayingMusic());
        }
    }

    public int getAlbumIndex() {
        return mPlayingInfoManager.getAlbumIndex();
    }

    public LiveData<ChangeMusic> getChangeMusicEvent() {
        return changeMusicLiveData;
    }

    public LiveData<PlayingMusic> getPlayingMusicEvent() {
        return playingMusicLiveData;
    }

    public LiveData<Boolean> getPauseEvent() {
        return pauseLiveData;
    }

    public LiveData<Enum> getPlayModeEvent() {
        return playModeLiveData;
    }

    public Enum getRepeatMode() {
        return mPlayingInfoManager.getRepeatMode();
    }

    public void togglePlay() {
        if (isPlaying()) {
            pauseAudio();
        } else {
            playAudio();
        }
    }

    public M getCurrentPlayingMusic() {
        return mPlayingInfoManager.getCurrentPlayingMusic();
    }

}
