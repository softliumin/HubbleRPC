package cc.sharper.test.struct;


/**
 * Created by liumin3 on 2016/10/19.
 */
public final class NettyMessage
{
    private Header header;
    private Object body;

    public Header getHeader() {
        return header;
    }

    public void setHeader(Header header) {
        this.header = header;
    }

    public Object getBody() {
        return body;
    }

    public void setBody(Object body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "NettyMessage{" +
                "header=" + header +
                ", object=" + body +
                '}';
    }
}
