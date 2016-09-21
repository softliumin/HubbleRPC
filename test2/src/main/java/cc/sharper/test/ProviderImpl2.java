package cc.sharper.test;

/**
 * Created by liumin3 on 2016/9/21.
 */
public class ProviderImpl2 implements  IProvider2
{
    public String testMethod(String ss)
    {
        System.out.println(ss);

        return "Test------>"+ss;
    }
}
