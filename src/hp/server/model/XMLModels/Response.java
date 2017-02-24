package hp.server.model.XMLModels;

/**
 * Created by Tautvilas on 24/02/2017.
 */
public class Response
{
    public int status;
    public String msg;

    public Response() {

    }

    public String getMsg()
    {
        return msg;
    }

    public void setMsg(String msg)
    {
        this.msg = msg;
    }

    public int getStatus()
    {
        return status;
    }

    public void setStatus(int status)
    {
        this.status = status;
    }

    @Override
    public String toString()
    {
        return "Response{" +
                "status=" + status +
                ", msg='" + msg + '\'' +
                '}';
    }
}
