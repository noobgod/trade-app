package com.xianjinxia.trade.app.request;

import javax.validation.constraints.NotNull;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: cff
 * Date: 2017-12-26
 * Time: 下午 4:39
 */
public class AuditOperationReq extends BaseRequest{

    @NotNull(message = "ids couldn't be null")
    private String ids;

    @NotNull(message = "operationType couldn't be null")
    private Integer operationType;

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public Integer getOperationType() {
        return operationType;
    }

    public void setOperationType(Integer operationType) {
        this.operationType = operationType;
    }
}
