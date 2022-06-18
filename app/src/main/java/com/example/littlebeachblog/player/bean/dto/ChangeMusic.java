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

package com.example.littlebeachblog.player.bean.dto;


import com.example.littlebeachblog.data.bean.TestAlbum;
import com.example.littlebeachblog.player.bean.base.BaseAlbumItem;
import com.example.littlebeachblog.player.bean.base.BaseArtistItem;
import com.example.littlebeachblog.player.bean.base.BaseMusicItem;

import java.io.Serializable;

/**
 * provide music info when music changed
 * <p>
 * Create by KunMinX at 18/9/24
 */
public class ChangeMusic<
        B extends BaseAlbumItem<M, A>,
        M extends BaseMusicItem<A>,
        A extends BaseArtistItem>
        implements Serializable {

    private String title;
    private String summary;
    private TestAlbum.TestMusic.ArBean artist;
    private String albumId;
    private String musicId;
    private String img;

    public ChangeMusic() {
    }

    public ChangeMusic(
            String title,
            String summary,
            String albumId,
            String musicId,
            String img,
            TestAlbum.TestMusic.ArBean artist
    ) {
        this.title = title;
        this.summary = summary;
        this.albumId = albumId;
        this.musicId = musicId;
        this.img = img;
        this.artist = artist;
    }

    public ChangeMusic(B musicAlbum, int playIndex) {
        this.title = musicAlbum.getSongs().get(playIndex).getName();
        this.summary = musicAlbum.getSummary();
        this.albumId = musicAlbum.getAlbumId();
        this.musicId = ((M) musicAlbum.getSongs().get(playIndex)).getMusicId();
        this.img = musicAlbum.getSongs().get(playIndex).getAl().getPicUrl();
        this.artist = musicAlbum.getSongs().get(playIndex).getAr().get(0);
    }

    public void setBaseInfo(B musicAlbum, M music) {
        //要用当前实际播放的列表，因为不同模式存在不同的播放列表
        this.title = music.getName();
        this.summary = musicAlbum.getSummary();
        this.albumId = musicAlbum.getAlbumId();
        this.musicId = music.getMusicId();
        this.img = music.getAl().getPicUrl();
        this.artist = music.getAr().get(0);
    }

    public String getTitle() {
        return title;
    }

    public TestAlbum.TestMusic.ArBean getArtist() {
        return artist;
    }

    public void setArtist(TestAlbum.TestMusic.ArBean artist) {
        this.artist = artist;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getAlbumId() {
        return albumId;
    }

    public void setAlbumId(String albumId) {
        this.albumId = albumId;
    }

    public String getMusicId() {
        return musicId;
    }

    public void setMusicId(String musicId) {
        this.musicId = musicId;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }


}
