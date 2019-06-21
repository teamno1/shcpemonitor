package cn.com.afcat.shcpemonitor.common.exception;

import cn.com.afcat.shcpemonitor.common.enums.ResultStatus;

public class ServiceException extends RuntimeException {
    private ResultStatus status;

    public ServiceException(ResultStatus status){
        super();
        this.status = status;
    }

    public ResultStatus getStatus() {
        return status;
    }

    public void setStatus(ResultStatus status) {
        this.status = status;
    }
}
