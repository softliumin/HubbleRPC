package cc.sharper.test;

/**
 * 测试的provider的实现类
 * Created by liumin3 on 2016/9/7.
 */
public class ProviderImpl implements  IProvider
{
    public String testMethod(String ss)
    {
        System.out.println(ss);

        return "Test------>"+ss;
    }
}
