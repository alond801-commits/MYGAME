package com.idodrori.mygame.screens;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.idodrori.mygame.R;
import com.idodrori.mygame.modle.Order;
import com.idodrori.mygame.adapters.OrderAdapter;
import com.idodrori.mygame.services.DatabaseService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AllOrders extends AppCompatActivity {

    RecyclerView rcAllordera;

    ArrayList<Order> orders = new ArrayList<>();
    OrderAdapter orderAdapter;
    SearchView svOrder;

    DatabaseService databaseService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_all_orders);


        databaseService = DatabaseService.getInstance();
        rcAllordera = findViewById(R.id.rcAllorders);

        rcAllordera.setLayoutManager(new LinearLayoutManager(this));
        rcAllordera.setFadingEdgeLength(50);

        orderAdapter = new OrderAdapter(orders, new OrderAdapter.OnOrderClickListener() {
            @Override
            public void onOrderClick(Order order) {
                Intent intent = new Intent(Intent.ACTION_DIAL);

                // אם אצלך זה בשם אחר תשנה את getPhone()
                intent.setData(Uri.parse("tel:" + order.getUser().getPhone()));

                startActivity(intent);



                // דיאלוג אחרי השיחה

                new AlertDialog.Builder(AllOrders.this)
                        .setTitle("עדכון הזמנה")
                        .setMessage("מה תרצה לעדכן לאחר השיחה?")
                        .setPositiveButton("הושלם", (dialog, which) -> {

                            // כאן תעשה עדכון להזמנה
                            // לדוגמה:
                            order.setStatus("done");
                            order.setDateHairCut(System.currentTimeMillis());
                            orderAdapter.notifyDataSetChanged();

                            databaseService.updateOreder(order, new DatabaseService.DatabaseCallback<Void>() {
                                @Override
                                public void onCompleted(Void object) {

                                }

                                @Override
                                public void onFailed(Exception e) {

                                }
                            });

                        })
                        .setNegativeButton("לא ענה", (dialog, which) -> {

                            //


                        })
                        .setNeutralButton("ביטול", null)
                        .show();
            }





            @Override
            public void onLongOrderClick(Order order) {

            }
        });
        rcAllordera.setAdapter(orderAdapter);

        databaseService.getAllOrders(new DatabaseService.DatabaseCallback<List<Order>>() {
            @Override
            public void onCompleted(List<Order> object) {
                orders.clear();
                orders.addAll(object);
                orderAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailed(Exception e) {
                // טיפול בשגיאה
            }
        });

        svOrder = findViewById(R.id.svOrders);
        svOrder.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filterOrders(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterOrders(newText);
                return true;
            }
        });
    }

    // הוצאת המתודה החוצה
    private void filterOrders(String query) {
        List<Order> filteredOrders = new ArrayList<>();
        String lowerCaseQuery = query.toLowerCase();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        for (Order order : orders) {
            String firstName = (order.getUser().getFname() != null) ? order.getUser().getFname().toLowerCase() : "";
            String lastName = (order.getUser().getLname() != null) ? order.getUser().getLname().toLowerCase() : "";
            String ostatus = (order.getStatus() != null) ? order.getStatus().toLowerCase() : "";
            // המרת Timestamp או Date למחרוזת
            String orderDate = "";
            if (false) {
                orderDate = sdf.format(order.getTimestamp()).toLowerCase();
            }

            if (firstName.contains(lowerCaseQuery) ||
                    lastName.contains(lowerCaseQuery) ||
                    orderDate.contains(lowerCaseQuery)||
                    ostatus.contains(lowerCaseQuery))
            {
                filteredOrders.add(order);
            }
        }

        orderAdapter.setOrders(filteredOrders);
        orderAdapter.notifyDataSetChanged();
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.admin_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.menu_add_haircut) {

            startActivity(new Intent(
                    AllOrders.this,
                    AddHairCut.class));
        }

        else if (id == R.id.menu_users) {

            startActivity(new Intent(
                    AllOrders.this,
                    UsersListActivity.class));
        }

        else if (id == R.id.menu_haircuts) {

            startActivity(new Intent(
                    AllOrders.this,
                    HairCutsListActivity.class));
        }

        else if (id == R.id.menu_orders) {

            startActivity(new Intent(
                    AllOrders.this,
                    AllOrders.class));
        }

        return true;
    }
}


