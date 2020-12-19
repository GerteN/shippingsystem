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

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Shipping{" +
                "orderId=" + orderId +
                ", userId=" + userId +
                '}';
    }
}
