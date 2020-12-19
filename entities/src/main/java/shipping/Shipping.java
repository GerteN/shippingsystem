package shipping;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class Shipping {

    @Id
    private Integer orderId;

    @NotNull(message = "The userID cannot be blank!")
    private Integer userId;

    public Integer getOrderId() {
        return orderId;
    }

    public Shipping setOrderId(Integer orderId) {
        this.orderId = orderId;
        return this;
    }

    public Integer getUserId() {
        return userId;
    }

    public Shipping setUserId(Integer userId) {
        this.userId = userId;
        return this;
    }

    @Override
    public String toString() {
        return "Shipping{" +
                "orderId=" + orderId +
                ", userId=" + userId +
                '}';
    }

}
