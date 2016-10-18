package cc.sharper.bean;

/**
 * hubble的返回对象
 * Created by liumin3 on 2016/9/14.
 */
public class HubbleResponse extends  BaseMessage
{
    private String requestId;

    private Throwable error;

    private Object result;

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public Throwable getError() {
        return error;
    }

    public void setError(Throwable error) {
        this.error = error;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

}
