package cc.sharper.test3;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 带权重的的随机轮训
 * Created by liumin3 on 2016/9/8.
 */
public class Test
{
    /**
     *
     * 这个实现太牛逼了，简单但是真的有效！
     * 测试随机数 带权重的随机数
     * @param args
     */
    public static void main(String[] args)
    {
        Xigua x1 = new Xigua(200);
        Xigua x2 = new Xigua(300);
        Xigua x3 = new Xigua(500);
        int s1 = 0;
        int s2 = 0;
        int s3 = 0;
        List<Xigua> list = new ArrayList<Xigua>();
        list.add(x1);
        list.add(x2);
        list.add(x3);
        Random random = new Random();
        for (int x =0;x<100000000;x++)
        {
            int rand = random.nextInt(1000);
            int sum= 0;
            for(int i=0;i<list.size();i++)
            {
                sum += list.get(i).getWeight();//武将的出现机率
                if(rand<=sum)
                {
                    int ss =  list.get(i).getWeight();
                    if(ss==200)
                    {
                        s1+=1;
                    }
                    if(ss==300)
                    {
                        s2+=1;
                    }
                    if(ss==500)
                    {
                        s3+=1;
                    }
                    break;
                }
            }
        }
        System.out.println("s1:"+s1);
        System.out.println("s2:"+s2);
        System.out.println("s3:"+s3);
    }
}

class Xigua
{
    private int weight;
    public  Xigua(int weight)
    {
        this.weight = weight;
    }
    public int getWeight()
    {
        return weight;
    }
    public void setWeight(int weight)
    {
        this.weight = weight;
    }
}
