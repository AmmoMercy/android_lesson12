package com.example.a1725121013_lichuanjian_lesson12;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Button newButton, finishButton, deleteButton;
    Spinner spinner;
    TextView textView;
    OrderDishDao orderDishDao;
    ArrayAdapter arrayAdapter;
    List<String> orderNames = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        newButton = findViewById(R.id.btn_new);
        finishButton = findViewById(R.id.btn_finish);
        deleteButton = findViewById(R.id.btn_delete);
        textView = findViewById(R.id.textView);
        spinner = findViewById(R.id.spn);
        AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "order_info")
                .allowMainThreadQueries()
                .build();
        orderDishDao = db.orderDishDao();
        initDishes();
        final List<CustomerOrder> orderList = orderDishDao.loadAllCustomerOrders();
        for (CustomerOrder order : orderList) {
            orderNames.add(order.getOrderName());
        }
        arrayAdapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_spinner_item, orderNames);

        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(this);
        newButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomerOrder customerOrder = new CustomerOrder("order_" + (int) (Math.random() * 10000), "Preparing\n");
                long orderId = orderDishDao.insertOneOrder(customerOrder);
                Random random = new Random();
                OrderDishCrossRef[] orderDishCrossRefs = new OrderDishCrossRef[3];
                orderDishCrossRefs[0] = new OrderDishCrossRef(orderId, random.nextInt(2) + 1);
                orderDishCrossRefs[1] = new OrderDishCrossRef(orderId, random.nextInt(2) + 3);
                orderDishCrossRefs[2] = new OrderDishCrossRef(orderId, random.nextInt(2) + 5);
                orderDishDao.insertMultiDishesForOneOrder(orderDishCrossRefs);
                orderNames.add(customerOrder.getOrderName());
                arrayAdapter.notifyDataSetChanged();
                spinner.setSelection(orderNames.size());

            }
        });
        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomerOrder customerOrder = orderDishDao.loadOneCustomerOrder(spinner.getSelectedItem().toString());
                customerOrder.setOrderStatus("已完成");
                orderDishDao.updateOneOrder(customerOrder);
                showOrderWithDishes(customerOrder.getOrderName());
            }
        });
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomerOrder customerOrder = orderDishDao.loadOneCustomerOrder(spinner.getSelectedItem().toString());
                orderDishDao.deleteOneOrder(customerOrder);
                orderNames.remove(customerOrder.getOrderName());
                showOrderWithDishes(orderNames.get(0));

            }
        });
    }

    private void initDishes() {
        SharedPreferences sharedPreferences = getPreferences(Context.MODE_PRIVATE);
        if (sharedPreferences.getBoolean("DishesExist", false) == false) {
            Dish[] dishes = new Dish[6];
            dishes[0] = new Dish("臭豆腐", 30);
            dishes[1] = new Dish("腐乳", 10);
            dishes[2] = new Dish("柠檬", 20);
            dishes[3] = new Dish("秘制小汉堡", 15);
            dishes[4] = new Dish("蓝罐奶酪", 100);
            dishes[5] = new Dish("鲱鱼罐头", 70);
            orderDishDao.insertMultiDishes(dishes);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("DishesExist", true);
            editor.apply();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String name = parent.getItemAtPosition(position).toString();
        if (parent.getId() == R.id.spn) {
            showOrderWithDishes(name);
        }

    }

    private void showOrderWithDishes(String name) {
        OrderWithDishes orderWithDishes = orderDishDao.getOrderWithDishes(name);
        String text = orderWithDishes.getCustomerOrder().getOrderName() + ":";
        text = text + orderWithDishes.getCustomerOrder().getOrderStatus();
        int totalPrice = 0;

        for (int i = 0; i < orderWithDishes.getDishes().size(); i++) {
            Dish dish = orderWithDishes.getDishes().get(i);
            text += (i + 1) + " " + dish.getDishName() + " " + dish.getDishPrice() + "元\n";
            totalPrice += dish.getDishPrice();
        }
        text += "共计:" + totalPrice + "元";
        System.out.println(text);
        textView.setText(text);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        textView.setText("Order Info");
    }
}
