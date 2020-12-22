package shipping;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class Shipping {

    @Id
    private Integer shippingId;

    @NotNull(message = "The userID cannot be blank!")
    private Integer userId;

    @NotNull(message = "The shippingAddress cannot be blank!")
    private String shippingAddress;

    public Integer getShippingId() { return shippingId; }

    public Shipping setShippingId(Integer shippingId) {
        this.shippingId = shippingId;
        return this;
    }

    public Integer getUserId() {
        return userId;
    }

    public Shipping setUserId(Integer userId) {
        this.userId = userId;
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
                ", userId=" + userId +
                ", shippingAddress='" + shippingAddress + '\'' +
                '}';
    }

}
