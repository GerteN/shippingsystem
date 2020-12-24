package org.project9.shipping.data;

import java.io.Serializable;

public class ShippingUpdateRequest implements Serializable {

    private Integer order_id;
    private String status;

    public Integer getOrder_id() {
        return order_id;
    }

    public ShippingUpdateRequest setOrder_id(Integer order_id) {
        this.order_id = order_id;
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
