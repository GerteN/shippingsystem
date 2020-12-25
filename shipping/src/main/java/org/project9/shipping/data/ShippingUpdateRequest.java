package org.project9.shipping.data;

import java.io.Serializable;

public class ShippingUpdateRequest implements Serializable {
    private Integer status;
    private Integer orderId;

    public Integer getStatus() {
        return status;
    }

    public void setStatusCode(Integer status) {
        this.status = status;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }
}
