package cc.sharper;

/**
 * Hubble 的常量表
 * Created by liumin3 on 2016/9/21.
 */
public class HubbleConstant
{
    public static String  zookeeper="";

    public static int  zookeeper_timeout=5000;

    public static String  zookeeper_privider="/hubble/provider/";

    public static String  zookeeper_consumer="/hubble/consumer/";

    /**
     * 默认协议类型:hubble
     */
    public final static ProtocolType DEFAULT_PROTOCOL_TYPE = ProtocolType.hubble;

    public enum ProtocolType {

        hubble(0),
        rest(1),
        webservice(2),
        http(3);

        private int value;

        private ProtocolType(int mvalue) {
            this.value = mvalue;
        }

        public int value() {
            return value;
        }

        public static ProtocolType valueOf(int value) {
            ProtocolType p;
            switch (value) {
                case 0:
                    p = hubble;
                    break;
                case 1:
                    p = rest;
                    break;
                case 2:
                    p = webservice;
                    break;
                case 3:
                    p = http;
                    break;
                default:
                    throw new IllegalArgumentException("Unknown protocol type value: " + value);
            }
            return p;
        }
    }

    /**
     * 默认序列话:msgpack
     */
    public final static CodecType DEFAULT_CODEC_TYPE = CodecType.msgpack;

    /**
     * 序列化方式类型,兼容1.x版本
     */
    public enum CodecType {

        protobuf(1),
        fastjson(2),
        java(3),
        msgpack(4),
        hessian(5);

        private int value;

        private CodecType(int mvalue) {
            this.value = mvalue;
        }

        public int value() {
            return value;
        }

        public static CodecType valueOf(int value) {
            CodecType p;
            switch (value) {
                case 1:
                    p = protobuf;
                    break;
                case 2:
                    p = fastjson;
                    break;
                case 3:
                    p = java;
                    break;
                case 4:
                    p = msgpack;
                    break;
                case 5:
                    p = hessian;
                    break;
                default:
                    throw new IllegalArgumentException("Unknown codec type value: " + value);
            }
            return p;
        }
    }

    /**
     * 压缩算法类型
     */
    public enum CompressType {

        NONE((byte) 0), lzma((byte) 1), snappy((byte) 2);

        private byte value;

        private CompressType(byte mvalue) {
            this.value = mvalue;
        }

        public byte value() {
            return value;
        }

        public static CompressType valueOf(byte value) {
            CompressType p;
            switch (value) {
                case 0:
                    p = NONE;
                    break;
                case 1:
                    p = lzma;
                    break;
                case 2:
                    p = snappy;
                    break;
                default:
                    throw new IllegalArgumentException(
                            "Unknown compress type value: " + value);
            }
            return p;
        }
    }


    /**
     * enum for head key number
     */
    public enum HeadKey {

        timeout((byte) 1, Integer.class), // 请求超时时间
        //interfaceId((byte) 2, String.class),
        //alias((byte) 3, String.class),
        //methodName((byte) 4, String.class),
        callbackInsId((byte) 5, String.class), // 回调函数对应的实例id
        //compress((byte) 6, String.class),
        jsfVersion((byte) 7, Short.class), // 客户端的JSF版本
        srcLanguage((byte) 8, Byte.class), // 请求的语言（针对跨语言 1c++ 2lua）
        responseCode((byte) 9, Byte.class), // 返回结果（针对跨语言 0成功 1失败）
        ;

        private byte keyNum;
        private Class type;


        private HeadKey(byte b, Class clazz) {
            this.keyNum = b;
            this.type = clazz;
        }

        public byte getNum() {
            return this.keyNum;
        }

        public Class getType() {
            return this.type;
        }

        public static HeadKey getKey(byte num) {
            HeadKey key = null;
            switch (num) {
                case 1:
                    key = timeout;
                    break;
                case 5:
                    key = callbackInsId;
                    break;
                case 7:
                    key = jsfVersion;
                    break;
                case 8:
                    key = srcLanguage;
                    break;
                case 9:
                    key = responseCode;
                    break;
                default:
                    throw new IllegalArgumentException("Unknown head key value: " + num);
            }
            return key;

        }

    }




}
