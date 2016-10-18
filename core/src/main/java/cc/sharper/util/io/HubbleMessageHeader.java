package cc.sharper.util.io;

import cc.sharper.HubbleConstant;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Hubble的消息头
 * 结构如下:
 *
 *  协议栈 魔术位
 *  ------
 *              length
 *  head----->  allLength
 *              msgType
 *              codecType
 *              msgId
 *
 *
 *  -------
 *  body
 *  ------
 *
 *
 * Created by liumin3 on 2016/9/23.
 */
public class HubbleMessageHeader implements Cloneable{

    private Integer length; // 总长度 包含magiccode + header + body

    private Short headerLength;


    /**
     * 协议栈
     */
    private int protocolType = HubbleConstant.DEFAULT_PROTOCOL_TYPE.value();

    /**
     * 序列化方式
     */
    private int codecType = HubbleConstant.DEFAULT_CODEC_TYPE.value();

    /**
     * 消息类型
     */
    private int msgType;


    /**
     * 消息id
     */
    private int msgId;

    /**
     * 压缩算法类型
     */
    private byte compressType = HubbleConstant.CompressType.NONE.value();

    private Map<Byte,Object> keysMap = new ConcurrentHashMap<Byte,Object>();

    public Map<Byte,Object> getAttrMap(){
        return this.keysMap;
    }

    public HubbleMessageHeader setValues(int protocolType, int codecType,
                                   int msgType, int compressType, int msgId) {
        this.msgId = msgId;
        this.codecType = codecType;
        this.msgType = msgType;
        this.protocolType = protocolType;
        this.compressType = (byte) compressType;
        return this;
    }

    public HubbleMessageHeader copyHeader(HubbleMessageHeader header){
        this.msgId = header.msgId;
        this.codecType = header.codecType;
        this.msgType = header.msgType;
        this.protocolType = header.getProtocolType();
        this.compressType = header.getCompressType();
        this.length = header.getLength();
        this.headerLength = header.getHeaderLength();
        Map<Byte,Object> tempMap = header.getAttrMap();
        for(Map.Entry<Byte,Object> entry:tempMap.entrySet()){
            this.keysMap.put(entry.getKey(),entry.getValue());
        }
        return this;
    }

    public Short getHeaderLength() {
        return headerLength;
    }

    public void setHeaderLength(Short headerLength) {
        this.headerLength = headerLength;
    }

    public int getMsgId() {
        return msgId;
    }

    public void setMsgId(int msgId) {
        this.msgId = msgId;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public int getCodecType() {
        return codecType;
    }

    public void setCodecType(int codecType) {
        this.codecType = codecType;
    }

    public int getMsgType() {
        return msgType;
    }

    public void setMsgType(int msgType) {
        this.msgType = msgType;
    }

    /**
     * @return the compressType
     */
    public byte getCompressType() {
        return compressType;
    }

    /**
     * @param compressType the compressType to set
     */
    public void setCompressType(byte compressType) {
        this.compressType = compressType;
    }

    public int getProtocolType() {
        return protocolType;
    }

    public void setProtocolType(int protocolType) {
        this.protocolType = protocolType;
    }

    public void addHeadKey(HubbleConstant.HeadKey key, Object value) {
        if (!key.getType().isInstance(value)) { // 检查类型
            throw new IllegalArgumentException("type mismatch of key:" + key.getNum() + ", expect:"
                    + key.getType().getName() + ", actual:" + value.getClass().getName());
        }
        keysMap.put(key.getNum(), value);
    }

    public Object removeByKey(HubbleConstant.HeadKey key){
        return keysMap.remove(key.getNum());
    }

    public Object getAttrByKey(HubbleConstant.HeadKey key){
        return keysMap.get(key.getNum());

    }

    public void setValuesInKeyMap(Map<Byte,Object> valueMap){
        this.keysMap.putAll(valueMap);

    }

    public int getAttrMapSize(){
        int mapSize = keysMap.size();
        return mapSize;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HubbleMessageHeader)) return false;

        HubbleMessageHeader that = (HubbleMessageHeader) o;

        if (codecType != that.codecType) return false;
        if (headerLength != null ? !headerLength.equals(that.headerLength) : that.headerLength != null) return false;
        if (msgId != that.msgId) return false;
        if (msgType != that.msgType) return false;
        if (protocolType != that.protocolType) return false;
        if (compressType != that.compressType) return false;
        if (length != null ? !length.equals(that.length) : that.length != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = msgId;
        result = 31 * result + (length != null ? length.hashCode() : 0);
        result = 31 * result + codecType;
        result = 31 * result + msgType;
        result = 31 * result + protocolType;
        result = 31 * result + compressType;
        result = 31 * result + (headerLength != null ? headerLength.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        String keymapStr = "";
        for(Map.Entry<Byte,Object> entry:keysMap.entrySet()){
            keymapStr = keymapStr+" "+ entry.getKey().toString()+" : "+entry.getValue().toString();
        }

        return "MessageHeader{" +
                "msgId=" + msgId +
                ", length=" + length +
                ", codecType=" + codecType +
                ", msgType=" + msgType +
                ", protocolType=" + protocolType +
                ", compressType=" + compressType +
                ", headerLength=" + headerLength +
                ", keysMap=" + keymapStr +
                "}";
    }

    /**
     * 克隆后和整体原来不是一个对象，
     * 属性相同，修改当前属性不会改变原来的
     * map和原来是一个对象，修改当前map也会改原来的
     *
     * @return
     * @throws CloneNotSupportedException
     */
    @Override
    public HubbleMessageHeader clone() {
        HubbleMessageHeader header = null;
        try {
            header = (HubbleMessageHeader) super.clone();
        } catch (CloneNotSupportedException e) {
            header = new HubbleMessageHeader();
            header.copyHeader(this);
        }
        return header;
    }
}
