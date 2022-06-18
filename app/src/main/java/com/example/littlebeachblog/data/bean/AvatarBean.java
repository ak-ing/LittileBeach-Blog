package com.example.littlebeachblog.data.bean;

import java.util.Objects;

/**
 * Created by AK on 2022/3/2 9:23.
 * God bless my code!
 */
public class AvatarBean {
    private String _id;
    private String picUrl;
    private int sort;
    private int __v;

    @Override
    public String toString() {
        return "AvatarBean{" +
                "_id='" + _id + '\'' +
                ", picUrl='" + picUrl + '\'' +
                ", sort=" + sort +
                ", __v=" + __v +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AvatarBean that = (AvatarBean) o;
        return sort == that.sort && __v == that.__v && Objects.equals(_id, that._id) && Objects.equals(picUrl, that.picUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(_id, picUrl, sort, __v);
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public int get__v() {
        return __v;
    }

    public void set__v(int __v) {
        this.__v = __v;
    }
}
