package cc.sharper.bean;

import cc.sharper.error.InitErrorException;

import java.io.Serializable;

/**
 * Created by liumin3 on 2016/9/13.
 */
public class ProviderConfig  extends   AbstractInterfaceConfig implements Serializable
{
    private static final long serialVersionUID = -2910502986608678372L;



    public synchronized void  create() throws InitErrorException
    {
        if (true)//直接加载，不延迟  以后可以配置
        {

        }
    }

}
