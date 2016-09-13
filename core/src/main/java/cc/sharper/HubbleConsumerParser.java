package cc.sharper;

import cc.sharper.bean.HubbleConsumer;
import cc.sharper.bean.HubbleRegistry;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

/**
 * 解析调用者  半成品
 * Created by liumin3 on 2016/9/2.
 */
public class HubbleConsumerParser   implements BeanDefinitionParser
{
    public BeanDefinition parse(Element element, ParserContext parserContext)
    {
        String id =  element.getAttribute("id");
        String inter = element.getAttribute("interface");
        String alias = element.getAttribute("alias");
        RootBeanDefinition beanDefinition = new RootBeanDefinition();
        beanDefinition.setBeanClass(HubbleConsumer.class);
        beanDefinition.setLazyInit(false);
        beanDefinition.getPropertyValues().add("id",id);
        beanDefinition.getPropertyValues().add("inter",inter);
        beanDefinition.getPropertyValues().add("alias",alias);
        parserContext.getRegistry().registerBeanDefinition(id, beanDefinition);//去容器注册bean
        return beanDefinition;
    }
}
