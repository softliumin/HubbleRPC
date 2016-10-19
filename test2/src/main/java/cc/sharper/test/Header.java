package cc.sharper.test;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Created by liumin3 on 2016/10/19.
 */
public class Header
{
    private int codec = 0xabef0101;
    private int length;
    private long sessinId;
    private byte type;
    private Map<String,Object> attach = new HashMap<String,Object>();

    public int getCodec() {
        return codec;
    }

    public void setCodec(int codec) {
        this.codec = codec;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public long getSessinId() {
        return sessinId;
    }

    public void setSessinId(long sessinId) {
        this.sessinId = sessinId;
    }

    public byte getType() {
        return type;
    }

    public void setType(byte type) {
        this.type = type;
    }

    public Map<String, Object> getAttach() {
        return attach;
    }

    public void setAttach(Map<String, Object> attach) {
        this.attach = attach;
    }
}
