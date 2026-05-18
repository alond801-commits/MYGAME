package com.idodrori.mygame.screens;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.idodrori.mygame.R;
import com.idodrori.mygame.modle.Order;
import com.idodrori.mygame.adapters.OrderAdapter;
import com.idodrori.mygame.services.DatabaseService;

import java.util.ArrayList;
import java.util.List;

public class UserOrders extends AppCompatActivity {
    RecyclerView rcAllordera;
    ArrayList<Order> orders = new ArrayList<>();
    OrderAdapter orderAdapter;

    DatabaseService databaseService;

    FirebaseAuth auth;

    String uid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user_orders);

        auth = FirebaseAuth.getInstance();

        uid = auth.getUid();


        databaseService = DatabaseService.getInstance();
        rcAllordera = findViewById(R.id.rcUserorders);

        rcAllordera.setLayoutManager(new LinearLayoutManager(this));

        rcAllordera.setFadingEdgeLength(50);

        orderAdapter = new OrderAdapter(orders, new OrderAdapter.OnOrderClickListener() {
            @Override
            public void onOrderClick(Order order) {

            }

            @Override
            public void onLongOrderClick(Order order) {

            }
        });
        rcAllordera.setAdapter(orderAdapter);
        databaseService.getUserOrders(uid, new DatabaseService.DatabaseCallback<List<Order>>() {
            @Override
            public void onCompleted(List<Order> object) {
                orders.clear();
                orders.addAll(object);
                orderAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailed(Exception e) {

            }
        });
    }


    @Override

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.user_menu, menu);
        return true;
    }

    @Override

    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.menu_haircuts) {
            Toast.makeText(this, "תספורות", Toast.LENGTH_SHORT).show();
            return true;
        }

        if (id == R.id.menu_add_haircut) {
            Toast.makeText(this, "הוסף", Toast.LENGTH_SHORT).show();
            return true;
        }

        if (id == R.id.menu_profile) {
            Toast.makeText(this, "פרופיל", Toast.LENGTH_SHORT).show();
            return true;
        }

        if (id == R.id.menu_orders) {
            Toast.makeText(this, "הזמנות", Toast.LENGTH_SHORT).show();
            return true;
        }

        if (id == R.id.menu_basket) {
            Toast.makeText(this, "סל", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
