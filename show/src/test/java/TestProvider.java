import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * 模拟提供者
 * Created by liumin3 on 2016/8/30.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/spring.xml")
public class TestProvider
{
    private Logger log = LoggerFactory.getLogger(getClass());

    @Test
    public  void test()
    {

    }


}
