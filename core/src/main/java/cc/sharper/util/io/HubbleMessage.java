package cc.sharper.util.io;

/**
 * hubble的消息结构
 * Created by liumin3 on 2016/9/23.
 */
public class HubbleMessage
{
    private HubbleMessageHeader header;
    private Object data;

    public HubbleMessage()
    {
    }

    public HubbleMessage(HubbleMessageHeader header)
    {
        this.header = header;
    }

    public HubbleMessage(HubbleMessageHeader header, Object data)
    {
        this.header = header;
        this.data = data;
    }

    public HubbleMessageHeader getHeader()
    {
        return header;
    }

    public void setHeader(HubbleMessageHeader header)
    {
        this.header = header;
    }

    public Object getData()
    {
        return data;
    }

    public void setData(Object data)
    {
        this.data = data;
    }
}
