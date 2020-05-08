package com.example.a1725121013_lichuanjian_lesson12;

import androidx.room.Embedded;
import androidx.room.Junction;
import androidx.room.Relation;

import java.util.List;

public class OrderWithDishes {
    @Embedded
    private CustomerOrder customerOrder;
    @Relation(
            parentColumn = "order_id",
            entityColumn = "dish_id",
            associateBy = @Junction(OrderDishCrossRef.class)
    )
    private List<Dish> dishes;

    public CustomerOrder getCustomerOrder() {
        return customerOrder;
    }

    public void setCustomerOrder(CustomerOrder customerOrder) {
        this.customerOrder = customerOrder;
    }

    public List<Dish> getDishes() {
        return dishes;
    }

    public void setDishes(List<Dish> dishes) {
        this.dishes = dishes;
    }
}
