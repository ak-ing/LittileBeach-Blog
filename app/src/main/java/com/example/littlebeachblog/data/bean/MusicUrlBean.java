package com.example.littlebeachblog.data.bean;

/**
 * Created by AK on 2022/2/17 11:36.
 * God bless my code!
 */
public class MusicUrlBean {

    private String id;
    private String url;
    private int br;
    private int size;
    private String md5;
    private int code;
    private int expi;
    private String type;
    private double gain;
    private int fee;
    private Object uf;
    private int payed;
    private int flag;
    private boolean canExtend;
    private Object freeTrialInfo;
    private String level;
    private String encodeType;
    private FreeTrialPrivilegeBean freeTrialPrivilege;
    private FreeTimeTrialPrivilegeBean freeTimeTrialPrivilege;
    private int urlSource;

    @Override
    public String toString() {
        return "MusicUrlBean{" +
                "id='" + id + '\'' +
                ", url='" + url + '\'' +
                ", br=" + br +
                ", size=" + size +
                ", md5='" + md5 + '\'' +
                ", code=" + code +
                ", expi=" + expi +
                ", type='" + type + '\'' +
                ", gain=" + gain +
                ", fee=" + fee +
                ", uf=" + uf +
                ", payed=" + payed +
                ", flag=" + flag +
                ", canExtend=" + canExtend +
                ", freeTrialInfo=" + freeTrialInfo +
                ", level='" + level + '\'' +
                ", encodeType='" + encodeType + '\'' +
                ", freeTrialPrivilege=" + freeTrialPrivilege +
                ", freeTimeTrialPrivilege=" + freeTimeTrialPrivilege +
                ", urlSource=" + urlSource +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getBr() {
        return br;
    }

    public void setBr(int br) {
        this.br = br;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public int getExpi() {
        return expi;
    }

    public void setExpi(int expi) {
        this.expi = expi;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getGain() {
        return gain;
    }

    public void setGain(double gain) {
        this.gain = gain;
    }

    public int getFee() {
        return fee;
    }

    public void setFee(int fee) {
        this.fee = fee;
    }

    public Object getUf() {
        return uf;
    }

    public void setUf(Object uf) {
        this.uf = uf;
    }

    public int getPayed() {
        return payed;
    }

    public void setPayed(int payed) {
        this.payed = payed;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public boolean isCanExtend() {
        return canExtend;
    }

    public void setCanExtend(boolean canExtend) {
        this.canExtend = canExtend;
    }

    public Object getFreeTrialInfo() {
        return freeTrialInfo;
    }

    public void setFreeTrialInfo(Object freeTrialInfo) {
        this.freeTrialInfo = freeTrialInfo;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getEncodeType() {
        return encodeType;
    }

    public void setEncodeType(String encodeType) {
        this.encodeType = encodeType;
    }

    public FreeTrialPrivilegeBean getFreeTrialPrivilege() {
        return freeTrialPrivilege;
    }

    public void setFreeTrialPrivilege(FreeTrialPrivilegeBean freeTrialPrivilege) {
        this.freeTrialPrivilege = freeTrialPrivilege;
    }

    public FreeTimeTrialPrivilegeBean getFreeTimeTrialPrivilege() {
        return freeTimeTrialPrivilege;
    }

    public void setFreeTimeTrialPrivilege(FreeTimeTrialPrivilegeBean freeTimeTrialPrivilege) {
        this.freeTimeTrialPrivilege = freeTimeTrialPrivilege;
    }

    public int getUrlSource() {
        return urlSource;
    }

    public void setUrlSource(int urlSource) {
        this.urlSource = urlSource;
    }

    public static class FreeTrialPrivilegeBean {
        private boolean resConsumable;
        private boolean userConsumable;
        private Object listenType;

        public boolean isResConsumable() {
            return resConsumable;
        }

        public void setResConsumable(boolean resConsumable) {
            this.resConsumable = resConsumable;
        }

        public boolean isUserConsumable() {
            return userConsumable;
        }

        public void setUserConsumable(boolean userConsumable) {
            this.userConsumable = userConsumable;
        }

        public Object getListenType() {
            return listenType;
        }

        public void setListenType(Object listenType) {
            this.listenType = listenType;
        }
    }

    public static class FreeTimeTrialPrivilegeBean {
        private boolean resConsumable;
        private boolean userConsumable;
        private int type;
        private int remainTime;

        public boolean isResConsumable() {
            return resConsumable;
        }

        public void setResConsumable(boolean resConsumable) {
            this.resConsumable = resConsumable;
        }

        public boolean isUserConsumable() {
            return userConsumable;
        }

        public void setUserConsumable(boolean userConsumable) {
            this.userConsumable = userConsumable;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public int getRemainTime() {
            return remainTime;
        }

        public void setRemainTime(int remainTime) {
            this.remainTime = remainTime;
        }
    }
}
