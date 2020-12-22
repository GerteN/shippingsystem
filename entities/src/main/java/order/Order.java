package order;

import product.Product;

import javax.persistence.*;
import java.util.List;

@Entity
public class Order {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer order_id;

    private Integer userId;

    public Integer getOrder_id() {
        return order_id;
    }

    public Order setOrder_id(Integer order_id) {
        this.order_id = order_id;
        return this;
    }

    public Integer getUserId() {
        return userId;
    }

    public Order setUserId(Integer userId) {
        this.userId = userId;
        return this;
    }

    @Override
    public String toString() {
        return "Order{" +
                "order_id=" + order_id +
                ", userId=" + userId +
                '}';
    }
}
