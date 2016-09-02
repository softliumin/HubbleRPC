package cc.sharper;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * Created by liumin3 on 2016/9/2.
 */
public class HubbleNamespaceHandler extends NamespaceHandlerSupport
{
    public void init()
    {
        registerBeanDefinitionParser("registry", new HubbleRegistryParser());
        registerBeanDefinitionParser("server", new HubbleServerParser());
        registerBeanDefinitionParser("provider", new HubbleProviderParser());
        registerBeanDefinitionParser("consumer", new HubbleConsumerParser());
    }
}
