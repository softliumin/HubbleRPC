package cc.sharper.test.common;

/**
 * Created by liumin3 on 2016/10/21.
 */
public enum MessageType {
    SERVICE_REQ((byte) 0),//请求
    SERVICE_RESP((byte) 1),//响应
    ONE_WAY((byte) 2),//单路
    LOGIN_REQ((byte) 3), //登录请求
    LOGIN_RESP((byte) 4),//登录响应
    HEARTBEAT_REQ((byte) 5),//心跳请求
    HEARTBEAT_RESP((byte) 6);//心跳响应

    private byte value;

    private MessageType(byte value) {
        this.value = value;
    }

    public byte value() {
        return this.value;
    }

}
