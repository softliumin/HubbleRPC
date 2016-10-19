package cc.sharper.test;

/**
 * Created by liumin3 on 2016/10/19.
 */
public final class NettyMessage
{
    private Header header;
    private Object object;

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    @Override
    public String toString() {
        return "NettyMessage{" +
                "header=" + header +
                ", object=" + object +
                '}';
    }
}
