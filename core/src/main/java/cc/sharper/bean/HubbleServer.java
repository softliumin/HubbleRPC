package cc.sharper.bean;

import org.springframework.beans.factory.InitializingBean;

/**
 * hubble server配置
 * Created by liumin3 on 2016/9/2.
 */
public class HubbleServer implements InitializingBean
{
    private String id;
    private String protocol;


    public void afterPropertiesSet() throws Exception
    {

    }
}
