package com.example.littlebeachblog.data.bean;

import java.util.Objects;

/**
 * Created by AK on 2022/2/12 16:56.
 * God bless my code!
 */
public class MoYv {

    private String _id;
    private String userId;
    private String content;
    private String images;
    private long createTime;
    private long updateTime;
    private int __v;
    private String topPicId;
    private UserInfo userInfo;
    private boolean isLike;
    private int likeCount;

    @Override
    public String toString() {
        return "MoYv{" +
                "_id='" + _id + '\'' +
                ", userId='" + userId + '\'' +
                ", content='" + content + '\'' +
                ", images='" + images + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", __v=" + __v +
                ", topPicId='" + topPicId + '\'' +
                ", userInfo=" + userInfo +
                ", isLike=" + isLike +
                ", likeCount=" + likeCount +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MoYv moYv = (MoYv) o;
        return createTime == moYv.createTime && updateTime == moYv.updateTime && __v == moYv.__v && isLike == moYv.isLike && likeCount == moYv.likeCount && Objects.equals(_id, moYv._id) && Objects.equals(userId, moYv.userId) && Objects.equals(content, moYv.content) && Objects.equals(images, moYv.images) && Objects.equals(topPicId, moYv.topPicId) && Objects.equals(userInfo, moYv.userInfo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(_id, userId, content, images, createTime, updateTime, __v, topPicId, userInfo, isLike, likeCount);
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTopPicId() {
        return topPicId;
    }

    public void setTopPicId(String topPicId) {
        this.topPicId = topPicId;
    }

    public boolean isLike() {
        return isLike;
    }

    public void setLike(boolean like) {
        isLike = like;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(long updateTime) {
        this.updateTime = updateTime;
    }

    public int get__v() {
        return __v;
    }

    public void set__v(int __v) {
        this.__v = __v;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public boolean isIsLike() {
        return isLike;
    }

    public void setIsLike(boolean isLike) {
        this.isLike = isLike;
    }

    public int getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(int likeCount) {
        this.likeCount = likeCount;
    }

    public static class UserInfo {
        private String _id;
        private String userName;
        private String nickName;
        private int score;
        private long createTime;
        private long updateTime;
        private long lostToken;
        private int __v;
        private String avatar;
        private String background;

        @Override
        public String toString() {
            return "UserInfo{" +
                    "_id='" + _id + '\'' +
                    ", userName='" + userName + '\'' +
                    ", nickName='" + nickName + '\'' +
                    ", score=" + score +
                    ", createTime=" + createTime +
                    ", updateTime=" + updateTime +
                    ", lostToken=" + lostToken +
                    ", __v=" + __v +
                    ", avatar='" + avatar + '\'' +
                    ", background='" + background + '\'' +
                    ", sex=" + sex +
                    ", label='" + label + '\'' +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            UserInfo userInfo = (UserInfo) o;
            return score == userInfo.score && createTime == userInfo.createTime && updateTime == userInfo.updateTime && lostToken == userInfo.lostToken && __v == userInfo.__v && sex == userInfo.sex && Objects.equals(_id, userInfo._id) && Objects.equals(userName, userInfo.userName) && Objects.equals(nickName, userInfo.nickName) && Objects.equals(avatar, userInfo.avatar) && Objects.equals(background, userInfo.background) && Objects.equals(label, userInfo.label);
        }

        @Override
        public int hashCode() {
            return Objects.hash(_id, userName, nickName, score, createTime, updateTime, lostToken, __v, avatar, background, sex, label);
        }

        public String getBackground() {
            return background;
        }

        public void setBackground(String background) {
            this.background = background;
        }

        private int sex;
        private String label;

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getNickName() {
            return nickName;
        }

        public void setNickName(String nickName) {
            this.nickName = nickName;
        }

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }

        public long getCreateTime() {
            return createTime;
        }

        public void setCreateTime(long createTime) {
            this.createTime = createTime;
        }

        public long getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(long updateTime) {
            this.updateTime = updateTime;
        }

        public long getLostToken() {
            return lostToken;
        }

        public void setLostToken(long lostToken) {
            this.lostToken = lostToken;
        }

        public int get__v() {
            return __v;
        }

        public void set__v(int __v) {
            this.__v = __v;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public int getSex() {
            return sex;
        }

        public void setSex(int sex) {
            this.sex = sex;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }
    }
}
