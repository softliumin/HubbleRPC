package cc.sharper;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 *
 * 总的解析自己定义的hubble标签！
 * Created by liumin3 on 2016/9/2.
 */
public class HubbleNamespaceHandler extends NamespaceHandlerSupport
{
    public void init()
    {
        registerBeanDefinitionParser("registry", new HubbleRegistryParser());
        registerBeanDefinitionParser("provider", new HubbleProviderParser());
        registerBeanDefinitionParser("consumer", new HubbleConsumerParser());

//        registerBeanDefinitionParser("server", new HubbleServerParser());
    }
}
