package cc.sharper;

import cc.sharper.bean.HubbleProvider;
import cc.sharper.bean.HubbleRegistry;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

/**
 * 解析提供者
 * Created by liumin3 on 2016/9/2.
 */
public class HubbleProviderParser   implements BeanDefinitionParser
{
    public BeanDefinition parse(Element element, ParserContext parserContext)
    {

        String id =  element.getAttribute("id");
        String ref = element.getAttribute("ref");
        String alias = element.getAttribute("alias");
        String inter = element.getAttribute("interface");

        RootBeanDefinition beanDefinition = new RootBeanDefinition();
        beanDefinition.setBeanClass(HubbleProvider.class);
        beanDefinition.setLazyInit(false);

        beanDefinition.getPropertyValues().addPropertyValue("id", id);
        beanDefinition.getPropertyValues().addPropertyValue("ref", ref);
        beanDefinition.getPropertyValues().addPropertyValue("alias", alias);
        beanDefinition.getPropertyValues().addPropertyValue("inter", inter);
        parserContext.getRegistry().registerBeanDefinition(id, beanDefinition);//去容器注册bean
        return  beanDefinition;
    }
}
