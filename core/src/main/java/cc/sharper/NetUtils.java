package cc.sharper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

/**
 * Created by liumin3 on 2016/9/8.
 */
public class NetUtils
{
    private final static Logger logger = LoggerFactory.getLogger(NetUtils.class);

    /**
     * 得到本机IPv4地址
     *
     * @return ip地址
     *
     */
    public static String getLocalHost() {
        InetAddress address = getLocalAddress();
        return address == null ? null : address.getHostAddress();
    }

    public static InetAddress getLocalAddress() {
        InetAddress localAddress = null;
        try {
            localAddress = InetAddress.getLocalHost();
            if (isValidAddress(localAddress)) {
                return localAddress;
            }
        } catch (Throwable e) {
            logger.warn("Error when retriving ip address: " + e.getMessage(), e);
        }
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            if (interfaces != null) {
                while (interfaces.hasMoreElements()) {
                    try {
                        NetworkInterface network = interfaces.nextElement();
                        Enumeration<InetAddress> addresses = network.getInetAddresses();
                        if (addresses != null) {
                            while (addresses.hasMoreElements()) {
                                try {
                                    InetAddress address = addresses.nextElement();
                                    if (isValidAddress(address)) {
                                        return address;
                                    }
                                } catch (Throwable e) {
                                    logger.warn("Error when retriving ip address: " + e.getMessage(), e);
                                }
                            }
                        }
                    } catch (Throwable e) {
                        logger.warn("Error when retriving ip address: " + e.getMessage(), e);
                    }
                }
            }
        } catch (Throwable e) {
            logger.warn("Error when retriving ip address: " + e.getMessage(), e);
        }
        logger.error("Can't get valid host, will use 127.0.0.1 instead.");
        return localAddress;
    }


    /**
     * 是否合法地址（非本地，非默认的IPv4地址）
     *
     * @param address
     *         InetAddress
     * @return 是否合法
     */
    private static boolean isValidAddress(InetAddress address)
    {
//        if (address == null || address.isLoopbackAddress())
//            return false;
//        String name = address.getHostAddress();
//        return (name != null
//                && !isAnyHost(name)
//                && !isLocalHost(name)
//                && isIPv4Host(name));

        return  true;
    }


}
