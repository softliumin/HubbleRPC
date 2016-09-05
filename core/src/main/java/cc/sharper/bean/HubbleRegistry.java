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

    private int address;

    private int procotolType;//协议名称

    private int codecType;//编码类型

    private String group;//组

    private int threadCount;//线程数



    public void destroy() throws Exception
    {

    }

    public void afterPropertiesSet() throws Exception
    {

    }
}
