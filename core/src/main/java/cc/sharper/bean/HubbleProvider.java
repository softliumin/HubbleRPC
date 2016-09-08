package cc.sharper.bean;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.xml.AbstractSimpleBeanDefinitionParser;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * Hubble的服务提供者
 * Created by liumin3 on 2016/9/2.
 */
public class HubbleProvider  extends AbstractSimpleBeanDefinitionParser  implements InitializingBean, DisposableBean, ApplicationContextAware, ApplicationListener,
        BeanNameAware
{
    private static final long serialVersionUID = -2508213661321690225L;

    private String id;
    private String ref;
    private String alias;
    private String inter;

    public void setBeanName(String name)
    {

    }

    public void destroy() throws Exception
    {

    }

    /**
     * 属性加载完成之后的执行
     * @throws Exception
     */
    public void afterPropertiesSet() throws Exception
    {

    }

    /**
     * 未知
     * @param applicationContext
     * @throws BeansException
     */
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException
    {

    }

    public void onApplicationEvent(ApplicationEvent applicationEvent)
    {
        //spring加载完毕
        if (applicationEvent instanceof ContextRefreshedEvent)
        {

        }
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getRef()
    {
        return ref;
    }

    public void setRef(String ref)
    {
        this.ref = ref;
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
}
