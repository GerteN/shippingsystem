package shipping;

import order.Order;
import product.Product;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
public class Shipping {

    @Id
    private Integer shippingId;

    private Integer order_id;

    @OneToMany
    private List<Product> products;

    @NotNull(message = "The shippingAddress cannot be blank!")
    private String shippingAddress;

    public Integer getShippingId() { return shippingId; }

    public Shipping setShippingId(Integer shippingId) {
        this.shippingId = shippingId;
        return this;
    }

    public String getShippingAddress() { return shippingAddress; }

    public Shipping setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
        return this;
    }

    @Override
    public String toString() {
        return "Shipping{" +
                "shippingId=" + shippingId +
                ", shippingAddress='" + shippingAddress + '\'' +
                '}';
    }

}
