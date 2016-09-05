package cc.sharper;

import cc.sharper.bean.HubbleConsumer;
import cc.sharper.bean.HubbleRegistry;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

/**
 * 解析调用者
 * Created by liumin3 on 2016/9/2.
 */
public class HubbleConsumerParser   implements BeanDefinitionParser
{
    public BeanDefinition parse(Element element, ParserContext parserContext)
    {
//        String interfacename = element.getAttribute("interfacename");
//        String id = element.getAttribute("id");
//        String group=element.getAttribute("group");
//        int procotolType=Integer.parseInt(element.getAttribute("procotolType"));
//        int codecType=Integer.parseInt(element.getAttribute("codecType"));
//        int timeout=Integer.parseInt(element.getAttribute("timeout"));

        RootBeanDefinition beanDefinition = new RootBeanDefinition();
//        beanDefinition.setBeanClass(HubbleConsumer.class);
//        beanDefinition.setLazyInit(false);
//
//        beanDefinition.getPropertyValues().addPropertyValue("interfacename", interfacename);
//        beanDefinition.getPropertyValues().addPropertyValue("group", group);
//        beanDefinition.getPropertyValues().addPropertyValue("protocolType", procotolType);
//        beanDefinition.getPropertyValues().addPropertyValue("codecType", codecType);
//        beanDefinition.getPropertyValues().addPropertyValue("timeout", timeout);
//
//        parserContext.getRegistry().registerBeanDefinition(id, beanDefinition);
        return beanDefinition;
    }
}
