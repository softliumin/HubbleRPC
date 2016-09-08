package cc.sharper.bean;

import org.springframework.beans.factory.xml.AbstractSimpleBeanDefinitionParser;

/**
 * Hubble的服务提供者
 * Created by liumin3 on 2016/9/2.
 */
public class HubbleProvider  extends AbstractSimpleBeanDefinitionParser
{
    private static final long serialVersionUID = -2508213661321690225L;

    private String id;
    private String ref;
    private String alias;
    private String inter;


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
