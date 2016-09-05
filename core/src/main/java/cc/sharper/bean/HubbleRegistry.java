package cc.sharper.bean;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * Created by liumin3 on 2016/9/2.
 */
public class HubbleRegistry  implements InitializingBean, DisposableBean
{
    private String id;

    private String protocol;

    private String address;





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
