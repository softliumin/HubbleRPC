package cc.sharper.test3.test;

import cc.sharper.test.IProvider;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 *
 *
 * 客户端的调用测试类
 *
 * Created by liumin3 on 2016/9/13.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/spring-config.xml")
public class Test
{
    private Logger log = LoggerFactory.getLogger(getClass());

    @Resource
    private IProvider iProvider;

    @org.junit.Test
    public  void test()
    {
        for (int x=0;x<1;x++)
        {
            String ss = iProvider.testMethod("ss");
            System.out.println(ss);
        }

        System.out.println("over");
       // log.error("测试");
    }
//    @org.junit.Test
//    public  void test2()
//    {
//        for (int x=0;x<100000;x++)
//        {
//            String ss = iProvider.testMethod("ss");
//            System.out.println(ss);
//        }
//
//
//        // log.error("测试");
//    }
//    @org.junit.Test
//    public  void test3()
//    {
//        for (int x=0;x<100000;x++)
//        {
//            String ss = iProvider.testMethod("ss");
//            System.out.println(ss);
//        }
//
//
//        // log.error("测试");
//    }
}
