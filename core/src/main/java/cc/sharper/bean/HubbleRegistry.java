package cc.sharper.bean;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import java.io.Serializable;

/**
 * Created by liumin3 on 2016/9/2.
 */
public class HubbleRegistry  implements InitializingBean, DisposableBean,Serializable
{
    private static final long serialVersionUID = -2508213661321690225L;

    private String id;

    private String protocol;

    private String address;



    public HubbleRegistry() {
    }

    public void destroy() throws Exception
    {

    }

    public void afterPropertiesSet() throws Exception
    {

    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getProtocol()
    {
        return protocol;
    }

    public void setProtocol(String protocol)
    {
        this.protocol = protocol;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }
}
