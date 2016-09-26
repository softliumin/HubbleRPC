package cc.sharper.bean;

import java.util.Map;

/**
 * Created by liumin3 on 2016/9/14.
 */
public class HubbleRequest extends  BaseMessage
{
    private String requestId;

    private String className;

    private String methodName;

    private Class<?>[] parameterTypes; // 参数类型

    private Object[] parameters; // 参数

    private Map<String ,String> args;// 附带信息

    public Map<String, String> getArgs()
    {
        return args;
    }

    public void setArgs(Map<String, String> args)
    {
        this.args = args;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Class<?>[] getParameterTypes() {
        return parameterTypes;
    }

    public void setParameterTypes(Class<?>[] parameterTypes) {
        this.parameterTypes = parameterTypes;
    }

    public Object[] getParameters() {
        return parameters;
    }

    public void setParameters(Object[] parameters) {
        this.parameters = parameters;
    }

}
