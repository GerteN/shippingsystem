package org.project9.shipping.data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ShippingCreateRequest implements Serializable {

    private Integer orderId;
    private Integer userId;
    private String shippingAddress;
    private Map<Integer,Integer> products = new HashMap<>();

    public String getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public Map<Integer, Integer> getProducts() {
        return products;
    }

    public void setProducts(Map<Integer, Integer> products) {
        this.products = products;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }


}
