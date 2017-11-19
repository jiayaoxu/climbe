package cn.newcode.climb.vo;

/**
 * @Description: 向客户端发送运行状态
 * @author: shine
 * @CreateDate: 2017/10/18 7:11
 * @Version: 1.0
 */
public class Status {
    private String Success;
    private String Error;

    public Status(){

    }

    public Status(String success,String error){
        this.Success = success;
        this.Error = error;
    }

    public String getSuccess() {
        return Success;
    }

    public void setSuccess(String success) {
        Success = success;
    }

    public String getError() {
        return Error;
    }

    public void setError(String error) {
        Error = error;
    }
}
