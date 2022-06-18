package com.example.littlebeachblog.data.bean;

import java.util.Objects;

/**
 * Created by AK on 2022/2/27 14:01.
 * God bless my code!
 */
public class ScoreBean {
    private String _id;
    private String userId;
    private int type;
    private int score;
    private String describe;
    private long createTime;
    private long updateTime;
    private int __v;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ScoreBean scoreBean = (ScoreBean) o;
        return type == scoreBean.type && score == scoreBean.score && createTime == scoreBean.createTime && updateTime == scoreBean.updateTime && __v == scoreBean.__v && Objects.equals(_id, scoreBean._id) && Objects.equals(userId, scoreBean.userId) && Objects.equals(describe, scoreBean.describe);
    }

    @Override
    public int hashCode() {
        return Objects.hash(_id, userId, type, score, describe, createTime, updateTime, __v);
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
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
}
