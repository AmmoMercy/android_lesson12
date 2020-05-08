package com.example.a1725121013_lichuanjian_lesson12;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;


@Dao
public interface OrderDishDao {
    @Insert
    long insertOneOrder(CustomerOrder customerOrder);

    @Insert
    void insertMultiDishes(Dish... dishes);

    @Insert
    void insertMultiDishesForOneOrder(OrderDishCrossRef... orderDishCrossRefs);

    @Query("SELECT * FROM CustomerOrder")
    List<CustomerOrder> loadAllCustomerOrders();

    @Query("SELECT * FROM CustomerOrder WHERE order_name=(:orderName)")
    CustomerOrder loadOneCustomerOrder(String orderName);

    @Transaction
    @Query("SELECT *FROM CustomerOrder WHERE order_name=(:orderName)")
    OrderWithDishes getOrderWithDishes(String orderName);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateOneOrder(CustomerOrder customerOrder);

    @Delete
    void deleteOneOrder(CustomerOrder customerOrder);


}
