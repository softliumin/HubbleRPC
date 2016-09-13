package cc.sharper.bean;

import cc.sharper.error.InitErrorException;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Hubble的服务调用者
 * Created by liumin3 on 2016/9/2.
 */
public class HubbleConsumer  implements InitializingBean, FactoryBean,
        ApplicationContextAware, DisposableBean, BeanNameAware
{
    private static final long serialVersionUID = -2508213661321690225L;

    private String id;
    private String alias;
    private String inter;

    private transient String beanName;




    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getAlias()
    {
        return alias;
    }

    public void setAlias(String alias)
    {
        this.alias = alias;
    }

    public String getInter()
    {
        return inter;
    }

    public void setInter(String inter)
    {
        this.inter = inter;
    }


    @Override
    public void setBeanName(String s)
    {
        this.beanName = s;
    }

    @Override
    public void destroy() throws Exception
    {

    }

    @Override
    public Object getObject() throws Exception
    {
        return null;
    }

    @Override
    public Class<?> getObjectType()
    {
        return null;
    }

    @Override
    public boolean isSingleton()
    {
        return true;
    }

    @Override
    public void afterPropertiesSet() throws Exception
    {

    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException
    {

    }



    //==============================下面的代码以后要抽离出去===========================

    private transient volatile Object proxyIns;
    protected String url;

    /**
     * 生成代理类
     * @return
     * @throws InitErrorException
     */
    public synchronized Object refer() throws InitErrorException
    {
        if (proxyIns != null) {
            return proxyIns;
        }



        return  proxyIns;
    }

    /**
     * 销毁对象
     */
    public synchronized void unrefer() {

    }


    //======================================================
}
