package com.example.a1725121013_lichuanjian_lesson12;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class CustomerOrder {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "order_id")
    private long orderId;
    @ColumnInfo(name = "order_name")
    private String orderName;
    @ColumnInfo(name = "order_status")
    private String orderStatus;

    public CustomerOrder(String orderName, String orderStatus) {
        this.orderName = orderName;
        this.orderStatus = orderStatus;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }
}
