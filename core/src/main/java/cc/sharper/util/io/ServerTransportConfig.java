package cc.sharper.util.io;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * Created by liumin3 on 2016/9/23.
 */
public class ServerTransportConfig
{
    private static final Logger log = LoggerFactory.getLogger(ServerTransportConfig.class);

    private int port =8080;

    private int TIMEOUT = 2000;

    private int CONNECTTIMEOUT = 5000;

    private int serverBusinessPoolSize = 100;

    private Map<String,String> parameters;//其他一些参数配置
}
