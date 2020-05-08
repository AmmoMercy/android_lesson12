package com.example.a1725121013_lichuanjian_lesson12;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {CustomerOrder.class, Dish.class,  OrderDishCrossRef.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract OrderDishDao orderDishDao();
}
