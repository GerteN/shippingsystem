package org.project9.shipping.data;

import java.io.Serializable;

public class ShippingUpdateRequest implements Serializable {

    private Integer orderId;
    private String status;

    public Integer getOrderId() {
        return orderId;
    }

    public ShippingUpdateRequest setOrderId(Integer orderId) {
        this.orderId = orderId;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public ShippingUpdateRequest setStatus(String status) {
        this.status = status;
        return this;
    }
}
