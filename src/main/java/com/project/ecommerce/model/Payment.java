package com.project.ecommerce.model;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "payments")
public class Payment {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Integer id;  // Fix: SQLite requires INTEGER PRIMARY KEY AUTOINCREMENT

    @Column(name = "order_id", nullable = false)
    private Integer orderId; // Ensure consistency with Order ID

    private String paymentStatus; // Example: "Success", "Failed"
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date paymentDate;

    // ✅ Default Constructor
    public Payment() {}

    // ✅ Parameterized Constructor
    public Payment(Integer id, Integer orderId, String paymentStatus, Date paymentDate) {
        this.id = id;
        this.orderId = orderId;
        this.paymentStatus = paymentStatus;
        this.paymentDate = paymentDate;
    }

    // ✅ Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }
}
