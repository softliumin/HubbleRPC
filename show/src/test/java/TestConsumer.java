import cc.sharper.bean.HubbleRegistry;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by liumin3 on 2016/8/30.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring.xml")
public class TestConsumer
{
    private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    public  void test()
    {

        HubbleRegistry registry = null;

        HubbleRegistry registry2 =(HubbleRegistry) applicationContext.getBean("hubbleRegistry");
//


//
//        String address = registry.getAddress();
//
//
//        System.out.println(address);
    }



}
