package com.example.miaoshademo.lock.anno;

/**
 * 锁枚举
 */
public enum SyncEnum {

    //结账锁
    CHECKOUT_SYNC("Necodeant7E",2,50000,300),
    //客人信息锁
    CUSTOM_WE_CHAT_INFO_SYNC("t74codeantqZCz",2,50000,1000);

    private String value;

    private int times;//获取次数（超过次数未获取到则获取失败）

    private long expireTime;//超时时间（毫秒）（防止释放锁时报错导致死锁的情况）

    private long interval;//每次重新获取间隔时间(毫秒)


    SyncEnum(String value, int times, long expireTime, long interval) {
        this.value = value;
        this.times = times;
        this.expireTime = expireTime;
        this.interval = interval;
    }


    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getTimes() {
        return times;
    }

    public void setTimes(int times) {
        this.times = times;
    }

    public long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(long expireTime) {
        this.expireTime = expireTime;
    }

    public long getInterval() {
        return interval;
    }

    public void setInterval(long interval) {
        this.interval = interval;
    }
}
