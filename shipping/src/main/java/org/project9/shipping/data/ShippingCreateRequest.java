package org.project9.shipping.data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ShippingCreateRequest implements Serializable {

    private Integer orderId;
    private Integer userId;
    private String shippingAddress;
    private Map<Integer,Integer> products = new HashMap<>();

    public Integer getOrderId() {
        return orderId;
    }

    public ShippingCreateRequest setOrderId(Integer orderId) {
        this.orderId = orderId;
        return this;
    }

    public Integer getUserId() {
        return userId;
    }

    public ShippingCreateRequest setUserId(Integer userId) {
        this.userId = userId;
        return this;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public ShippingCreateRequest setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
        return this;
    }

    public Map<Integer, Integer> getProducts() {
        return products;
    }

    public ShippingCreateRequest setProducts(Map<Integer, Integer> products) {
        this.products = products;
        return this;
    }

    @Override
    public String toString() {
        return "ShippingCreateRequest{" +
                "orderId=" + orderId +
                ", userId=" + userId +
                ", shippingAddress='" + shippingAddress + '\'' +
                ", products=" + products +
                '}';
    }

}
