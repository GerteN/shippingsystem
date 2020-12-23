package shipping;

import javax.enterprise.inject.Default;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

@Entity
public class Shipping {

    @Id
    private Integer shippingId;

    @NotNull(message = "The order_id cannot be blank!")
    @Column(unique = true)
    private Integer order_id;

    @NotNull(message = "The userId cannot be blank!")
    private Integer userId;

    @NotNull(message = "The shippingAddress cannot be blank!")
    private String shippingAddress;

    @GeneratedValue(strategy=GenerationType.AUTO)
    @NotNull(message = "The products cannot be blank!")
    @ElementCollection
    private Map<Integer,Integer> products;

    @NotNull(message = "The status cannot be blank!")
    private String status;

    @GeneratedValue(strategy=GenerationType.AUTO)
    @NotNull(message = "The DDT cannot be blank!")
    private Integer DDT;

    public Integer getShippingId() {
        return shippingId;
    }

    public Shipping setShippingId(Integer shippingId) {
        this.shippingId = shippingId;
        return this;
    }

    public Integer getOrder_id() {
        return order_id;
    }

    public Shipping setOrder_id(Integer order_id) {
        this.order_id = order_id;
        return this;
    }

    public Integer getUserId() {
        return userId;
    }

    public Shipping setUserId(Integer userId) {
        this.userId = userId;
        return this;
    }

    public String getShippingAddress() {
        return shippingAddress;
    }

    public Shipping setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
        return this;
    }

    public Map<Integer, Integer> getProducts() {
        return products;
    }

    public Shipping setProducts(Map<Integer, Integer> products) {
        this.products = products;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public Shipping setStatus(String status) {
        this.status = status;
        return this;
    }

    public Integer getDDT() {
        return DDT;
    }

    public Shipping setDDT(Integer DDT) {
        this.DDT = DDT;
        return this;
    }

    @Override
    public String toString() {
        return "Shipping{" +
                "shippingId=" + shippingId +
                ", order_id=" + order_id +
                ", userId=" + userId +
                ", shippingAddress='" + shippingAddress + '\'' +
                ", products=" + products +
                ", status='" + status + '\'' +
                ", DDT=" + DDT +
                '}';
    }

}