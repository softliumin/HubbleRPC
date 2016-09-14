package cc.sharper.test3.test;

import cc.sharper.test.IProvider;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by liumin3 on 2016/9/13.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/spring-config.xml")
public class Test
{
    private Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private IProvider iProvider;

    @org.junit.Test
    public  void test()
    {
//        String ss = iProvider.testMethod("ss");
//
//        System.out.println(ss);
//        log.error("测试");
    }
}
