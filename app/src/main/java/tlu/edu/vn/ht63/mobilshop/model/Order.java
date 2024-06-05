package tlu.edu.vn.ht63.mobilshop.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "orders")
public class Order {
    @PrimaryKey(autoGenerate = true)
    private int orderId;
    private String employeeId;  // Sử dụng employeeId thay vì username để lưu mã nhân viên
    private float totalPrice;
    private float discountedPrice;

    // Constructors, getters and setters
    public Order(String employeeId, float totalPrice, float discountedPrice) {
        this.employeeId = employeeId;
        this.totalPrice = totalPrice;
        this.discountedPrice = discountedPrice;
    }

    // Getters and Setters
    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public float getDiscountedPrice() {
        return discountedPrice;
    }

    public void setDiscountedPrice(float discountedPrice) {
        this.discountedPrice = discountedPrice;
    }
}
