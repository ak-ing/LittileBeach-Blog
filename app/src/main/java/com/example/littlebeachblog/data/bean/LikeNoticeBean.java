package com.example.littlebeachblog.data.bean;

import androidx.annotation.NonNull;

import java.util.List;
import java.util.Objects;

/**
 * Created by AK on 2022/2/19 17:22.
 * God bless my code!
 */
public class LikeNoticeBean {

    private List<LikesBean> likes;
    private int unReadCount;

    public List<LikesBean> getLikes() {
        return likes;
    }


    public void setLikes(List<LikesBean> likes) {
        this.likes = likes;
    }

    public int getUnReadCount() {
        return unReadCount;
    }

    public void setUnReadCount(int unReadCount) {
        this.unReadCount = unReadCount;
    }

    public static class LikesBean implements Cloneable {
        private String _id;
        private String fishId;
        private String userId;
        private String doUserId;
        private int type;
        private long createTime;
        private long updateTime;
        private int __v;
        private FishContentBean fishContent;
        private UserInfoBean userInfo;

        @NonNull
        @Override
        public LikesBean clone() {
            LikesBean o = null;
            try {
                o = (LikesBean) super.clone();
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
            return o;
        }

        public LikesBean(String _id, String fishId, String userId, String doUserId, int type, long createTime, long updateTime, int __v, FishContentBean fishContent, UserInfoBean userInfo) {
            this._id = _id;
            this.fishId = fishId;
            this.userId = userId;
            this.doUserId = doUserId;
            this.type = type;
            this.createTime = createTime;
            this.updateTime = updateTime;
            this.__v = __v;
            this.fishContent = fishContent;
            this.userInfo = userInfo;
        }

        @Override
        public String toString() {
            return "LikesBean{" +
                    "_id='" + _id + '\'' +
                    ", fishId='" + fishId + '\'' +
                    ", userId='" + userId + '\'' +
                    ", doUserId='" + doUserId + '\'' +
                    ", type=" + type +
                    ", createTime=" + createTime +
                    ", updateTime=" + updateTime +
                    ", __v=" + __v +
                    ", fishContent=" + fishContent +
                    ", userInfo=" + userInfo +
                    '}';
        }


        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            LikesBean likesBean = (LikesBean) o;
            return type == likesBean.type && createTime == likesBean.createTime && updateTime == likesBean.updateTime && __v == likesBean.__v && Objects.equals(_id, likesBean._id) && Objects.equals(fishId, likesBean.fishId) && Objects.equals(userId, likesBean.userId) && Objects.equals(doUserId, likesBean.doUserId) && Objects.equals(fishContent, likesBean.fishContent) && Objects.equals(userInfo, likesBean.userInfo);
        }

        @Override
        public int hashCode() {
            return Objects.hash(_id, fishId, userId, doUserId, type, createTime, updateTime, __v, fishContent, userInfo);
        }

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getFishId() {
            return fishId;
        }

        public void setFishId(String fishId) {
            this.fishId = fishId;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getDoUserId() {
            return doUserId;
        }

        public void setDoUserId(String doUserId) {
            this.doUserId = doUserId;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
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

        public FishContentBean getFishContent() {
            return fishContent;
        }

        public void setFishContent(FishContentBean fishContent) {
            this.fishContent = fishContent;
        }

        public UserInfoBean getUserInfo() {
            return userInfo;
        }

        public void setUserInfo(UserInfoBean userInfo) {
            this.userInfo = userInfo;
        }

        public static class FishContentBean {
            @Override
            public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                FishContentBean that = (FishContentBean) o;
                return Objects.equals(content, that.content);
            }

            @Override
            public int hashCode() {
                return Objects.hash(content);
            }

            private String content;

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }
        }

        public static class UserInfoBean {
            private String _id;
            private String userName;
            private String nickName;
            private int score;
            private long createTime;
            private long updateTime;
            private long lostToken;
            private int __v;
            private String avatar;
            private int sex;
            private String label;

            @Override
            public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                UserInfoBean that = (UserInfoBean) o;
                return score == that.score && createTime == that.createTime && updateTime == that.updateTime && lostToken == that.lostToken && __v == that.__v && sex == that.sex && Objects.equals(_id, that._id) && Objects.equals(userName, that.userName) && Objects.equals(nickName, that.nickName) && Objects.equals(avatar, that.avatar) && Objects.equals(label, that.label);
            }

            @Override
            public int hashCode() {
                return Objects.hash(_id, userName, nickName, score, createTime, updateTime, lostToken, __v, avatar, sex, label);
            }

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
}
