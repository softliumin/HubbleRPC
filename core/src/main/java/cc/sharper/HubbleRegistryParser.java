package cc.sharper;

import cc.sharper.bean.HubbleRegistry;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * 解析注册中心
 * Created by liumin3 on 2016/9/2.
 */
public class HubbleRegistryParser  implements BeanDefinitionParser
{
    public BeanDefinition parse(Element element, ParserContext parserContext)
    {

        String protocol  = element.getAttribute("protocol");
        String address = element.getAttribute("address");
        String id = element.getAttribute("id");

//        String interfacename = element.getAttribute("interfacename");
//        String id = element.getAttribute("id");
//        String group=element.getAttribute("group");
//        int procotolType=Integer.parseInt(element.getAttribute("procotolType"));
//        int codecType=Integer.parseInt(element.getAttribute("codecType"));
//        int timeout=Integer.parseInt(element.getAttribute("timeout"));


//        beanDefinition.setBeanClass(HubbleRegistry.class);
//        beanDefinition.setLazyInit(false);
//
//        beanDefinition.getPropertyValues().addPropertyValue("interfacename", interfacename);
//        beanDefinition.getPropertyValues().addPropertyValue("group", group);
//        beanDefinition.getPropertyValues().addPropertyValue("protocolType", procotolType);
//        beanDefinition.getPropertyValues().addPropertyValue("codecType", codecType);
//        beanDefinition.getPropertyValues().addPropertyValue("timeout", timeout);
//
//        parserContext.getRegistry().registerBeanDefinition(id, beanDefinition);
        RootBeanDefinition beanDefinition = new RootBeanDefinition();
        beanDefinition.setLazyInit(false);
        beanDefinition.setBeanClass(HubbleRegistry.class);

        //设置属性
//        Method[] methods = HubbleRegistry.class.getMethods();

        beanDefinition.getPropertyValues().addPropertyValue("protocol",protocol);
        beanDefinition.getPropertyValues().addPropertyValue("address",address);
        beanDefinition.getPropertyValues().addPropertyValue("id",id);

        parserContext.getRegistry().registerBeanDefinition(id, beanDefinition);

        return beanDefinition;
    }


    //按照对象对象的属性来赋值

    public boolean isProperty(Method method,Class beanClass)
    {
        String methodName = method.getName();
        boolean flag = methodName.length()>3 && methodName.startsWith("set") && Modifier.isPublic(method.getModifiers()) && method.getParameterTypes().length==1;
        Method getter = null;
        if(flag)
        {
            Class type = method.getParameterTypes()[0];
            try
            {
                //TODO
                getter = beanClass.getMethod("get" + methodName.substring(3), new Class[0]);
            }catch (NoSuchMethodException e)
            {
                try
                {
                    getter = beanClass.getMethod("is" + methodName.substring(3), new Class[0]);
                }catch (NoSuchMethodException e2)
                {
                    ;
                }
            }
        }
        return  flag;
    }


}
