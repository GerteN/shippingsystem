package shipping;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Map;

@Entity
public class Shipping {

    @Id
    private Integer shippingId;

    @NotNull(message = "The order ID cannot be blank!")
    @Column(unique=true)
    private Integer orderId;

    @NotNull(message = "The user ID cannot be blank!")
    private Integer userId;

    @NotNull(message = "The shipping address cannot be blank!")
    private String shippingAddress;

    @NotNull(message = "The products cannot be blank!")
    @ElementCollection
    private Map<Integer,Integer> products;

    @NotNull(message = "The status cannot be blank!")
    private String status;

    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer DDT;

    public Integer getShippingId() {
        return shippingId;
    }

    public Shipping setShippingId(Integer shippingId) {
        this.shippingId = shippingId;
        return this;
    }

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
        return "The Shipping (with id = "+ shippingId +")" + " has: " +
                "\norderId = " + orderId +
                "\nuserId = " + userId +
                "\nshippingAddress = " + shippingAddress +
                "\nproducts = " + products +
                "\nstatus = " + status +
                "\nDDT =" + DDT;
    }

}