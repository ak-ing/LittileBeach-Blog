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

package com.example.littlebeachblog.player.bean.base;

import com.example.littlebeachblog.data.bean.TestAlbum;
import com.example.littlebeachblog.data.room.PlayHistoryEntity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 音乐实体，music bean
 * <p>
 * Create by KunMinX at 18/9/24
 */
public class BaseMusicItem<A extends BaseArtistItem> implements Serializable {

    private String musicId;
    private String coverImg;
    private String url;
    private String title;
    private A artist;

    private String name;
    private String id;
    private int pst;
    private int t;
    private List<TestAlbum.TestMusic.ArBean> ar;
    private List<String> alia;
    private int pop;
    private int st;
    private Object rt;
    private int fee;
    private int v;
    private Object crbt;
    private String cf;
    private TestAlbum.TestMusic.AlBean al;
    private int dt;
    private TestAlbum.TestMusic.HBean h;
    private TestAlbum.TestMusic.MBean m;
    private TestAlbum.TestMusic.LBean l;
    private Object a;
    private String cd;
    private int no;
    private Object rtUrl;
    private int ftype;
    private List<?> rtUrls;
    private int djId;
    private int copyright;
    private int s_id;
    private int mark;
    private int originCoverType;
    private Object originSongSimpleData;
    private Object tagPicList;
    private boolean resourceState;
    private int version;
    private Object songJumpInfo;
    private Object entertainmentTags;
    private int single;
    private Object noCopyrightRcmd;
    private int mst;
    private int cp;
    private int rtype;
    private Object rurl;
    private int mv;
    private long publishTime;

    public PlayHistoryEntity asRoomEntity() {
        ArBean arBean = ar.get(0);
        return new PlayHistoryEntity(id, name, arBean.id, arBean.name, al.id, al.name, al.pic, al.picUrl, new Date().getTime());
    }

    public String getMusicId() {
        return getId();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getPst() {
        return pst;
    }

    public void setPst(int pst) {
        this.pst = pst;
    }

    public int getT() {
        return t;
    }

    public void setT(int t) {
        this.t = t;
    }

    public List<TestAlbum.TestMusic.ArBean> getAr() {
        return ar;
    }

    public void setAr(List<TestAlbum.TestMusic.ArBean> ar) {
        this.ar = ar;
    }

    public List<String> getAlia() {
        return alia;
    }

    public void setAlia(List<String> alia) {
        this.alia = alia;
    }

    public int getPop() {
        return pop;
    }

    public void setPop(int pop) {
        this.pop = pop;
    }

    public int getSt() {
        return st;
    }

    public void setSt(int st) {
        this.st = st;
    }

    public Object getRt() {
        return rt;
    }

    public void setRt(Object rt) {
        this.rt = rt;
    }

    public int getFee() {
        return fee;
    }

    public void setFee(int fee) {
        this.fee = fee;
    }

    public int getV() {
        return v;
    }

    public void setV(int v) {
        this.v = v;
    }

    public Object getCrbt() {
        return crbt;
    }

    public void setCrbt(Object crbt) {
        this.crbt = crbt;
    }

    public String getCf() {
        return cf;
    }

    public void setCf(String cf) {
        this.cf = cf;
    }

    public TestAlbum.TestMusic.AlBean getAl() {
        return al;
    }

    public void setAl(TestAlbum.TestMusic.AlBean al) {
        this.al = al;
    }

    public int getDt() {
        return dt;
    }

    public void setDt(int dt) {
        this.dt = dt;
    }

    public TestAlbum.TestMusic.HBean getH() {
        return h;
    }

    public void setH(TestAlbum.TestMusic.HBean h) {
        this.h = h;
    }

    public TestAlbum.TestMusic.MBean getM() {
        return m;
    }

    public void setM(TestAlbum.TestMusic.MBean m) {
        this.m = m;
    }

    public TestAlbum.TestMusic.LBean getL() {
        return l;
    }

    public void setL(TestAlbum.TestMusic.LBean l) {
        this.l = l;
    }

    public Object getA() {
        return a;
    }

    public void setA(Object a) {
        this.a = a;
    }

    public String getCd() {
        return cd;
    }

    public void setCd(String cd) {
        this.cd = cd;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public Object getRtUrl() {
        return rtUrl;
    }

    public void setRtUrl(Object rtUrl) {
        this.rtUrl = rtUrl;
    }

    public int getFtype() {
        return ftype;
    }

    public void setFtype(int ftype) {
        this.ftype = ftype;
    }

    public List<?> getRtUrls() {
        return rtUrls;
    }

    public void setRtUrls(List<?> rtUrls) {
        this.rtUrls = rtUrls;
    }

    public int getDjId() {
        return djId;
    }

    public void setDjId(int djId) {
        this.djId = djId;
    }

    public int getCopyright() {
        return copyright;
    }

    public void setCopyright(int copyright) {
        this.copyright = copyright;
    }

    public int getS_id() {
        return s_id;
    }

    public void setS_id(int s_id) {
        this.s_id = s_id;
    }

    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    public int getOriginCoverType() {
        return originCoverType;
    }

    public void setOriginCoverType(int originCoverType) {
        this.originCoverType = originCoverType;
    }

    public Object getOriginSongSimpleData() {
        return originSongSimpleData;
    }

    public void setOriginSongSimpleData(Object originSongSimpleData) {
        this.originSongSimpleData = originSongSimpleData;
    }

    public Object getTagPicList() {
        return tagPicList;
    }

    public void setTagPicList(Object tagPicList) {
        this.tagPicList = tagPicList;
    }

    public boolean isResourceState() {
        return resourceState;
    }

    public void setResourceState(boolean resourceState) {
        this.resourceState = resourceState;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public Object getSongJumpInfo() {
        return songJumpInfo;
    }

    public void setSongJumpInfo(Object songJumpInfo) {
        this.songJumpInfo = songJumpInfo;
    }

    public Object getEntertainmentTags() {
        return entertainmentTags;
    }

    public void setEntertainmentTags(Object entertainmentTags) {
        this.entertainmentTags = entertainmentTags;
    }

    public int getSingle() {
        return single;
    }

    public void setSingle(int single) {
        this.single = single;
    }

    public Object getNoCopyrightRcmd() {
        return noCopyrightRcmd;
    }

    public void setNoCopyrightRcmd(Object noCopyrightRcmd) {
        this.noCopyrightRcmd = noCopyrightRcmd;
    }

    public int getMst() {
        return mst;
    }

    public void setMst(int mst) {
        this.mst = mst;
    }

    public int getCp() {
        return cp;
    }

    public void setCp(int cp) {
        this.cp = cp;
    }

    public int getRtype() {
        return rtype;
    }

    public void setRtype(int rtype) {
        this.rtype = rtype;
    }

    public Object getRurl() {
        return rurl;
    }

    public void setRurl(Object rurl) {
        this.rurl = rurl;
    }

    public int getMv() {
        return mv;
    }

    public void setMv(int mv) {
        this.mv = mv;
    }

    public long getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(long publishTime) {
        this.publishTime = publishTime;
    }

    public static class AlBean {
        private int id;
        private String name;
        private String picUrl;
        private List<?> tns;
        private String pic_str;
        private long pic;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getPicUrl() {
            return picUrl;
        }

        public void setPicUrl(String picUrl) {
            this.picUrl = picUrl;
        }

        public List<?> getTns() {
            return tns;
        }

        public void setTns(List<?> tns) {
            this.tns = tns;
        }

        public String getPic_str() {
            return pic_str;
        }

        public void setPic_str(String pic_str) {
            this.pic_str = pic_str;
        }

        public long getPic() {
            return pic;
        }

        public void setPic(long pic) {
            this.pic = pic;
        }
    }

    public static class HBean {
        private int br;
        private int fid;
        private int size;
        private int vd;

        public int getBr() {
            return br;
        }

        public void setBr(int br) {
            this.br = br;
        }

        public int getFid() {
            return fid;
        }

        public void setFid(int fid) {
            this.fid = fid;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public int getVd() {
            return vd;
        }

        public void setVd(int vd) {
            this.vd = vd;
        }
    }

    public static class MBean {
        private int br;
        private int fid;
        private int size;
        private int vd;

        public int getBr() {
            return br;
        }

        public void setBr(int br) {
            this.br = br;
        }

        public int getFid() {
            return fid;
        }

        public void setFid(int fid) {
            this.fid = fid;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public int getVd() {
            return vd;
        }

        public void setVd(int vd) {
            this.vd = vd;
        }
    }

    public static class LBean {
        private int br;
        private int fid;
        private int size;
        private int vd;

        public int getBr() {
            return br;
        }

        public void setBr(int br) {
            this.br = br;
        }

        public int getFid() {
            return fid;
        }

        public void setFid(int fid) {
            this.fid = fid;
        }

        public int getSize() {
            return size;
        }

        public void setSize(int size) {
            this.size = size;
        }

        public int getVd() {
            return vd;
        }

        public void setVd(int vd) {
            this.vd = vd;
        }
    }

    public static class ArBean {
        private int id;
        private String name;
        private List<?> tns;
        private List<?> alias;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<?> getTns() {
            return tns;
        }

        public void setTns(List<?> tns) {
            this.tns = tns;
        }

        public List<?> getAlias() {
            return alias;
        }

        public void setAlias(List<?> alias) {
            this.alias = alias;
        }
    }

    public BaseMusicItem() {
    }

    public BaseMusicItem(
            String musicId,
            String coverImg,
            String url,
            String title,
            A artist
    ) {
        this.musicId = musicId;
        this.coverImg = coverImg;
        this.url = url;
        this.title = title;
        this.artist = artist;
    }


    public void setMusicId(String musicId) {
        this.musicId = musicId;
    }

    public String getCoverImg() {
        return coverImg;
    }

    public void setCoverImg(String coverImg) {
        this.coverImg = coverImg;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public A getArtist() {
        return artist;
    }

    public void setArtist(A artist) {
        this.artist = artist;
    }
}
