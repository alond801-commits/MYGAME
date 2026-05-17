package com.idodrori.mygame.screens;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.idodrori.mygame.R;
import com.idodrori.mygame.adapters.CartAdapter;
import com.idodrori.mygame.modle.Order;
import com.idodrori.mygame.modle.Cart;
import com.idodrori.mygame.modle.User;
import com.idodrori.mygame.services.DatabaseService;


public class Shopping_basket extends AppCompatActivity implements View.OnClickListener {
    private static final String CART_PREFS = "CartPrefs";
    private static final String CART_KEY = "cartItems";
    RecyclerView rcCart;
    Cart cart = null;
    Button btnBePayment;
    TextView tvprice;
    DatabaseService databaseService;
    FirebaseAuth authenticationService;
    String uid;
    User user = null;
    private CartAdapter cartAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_basket);
        btnBePayment = findViewById(R.id.btnBeyondPayment);
        btnBePayment.setOnClickListener(this);

        rcCart = findViewById(R.id.rcvShoppingBasket);
        tvprice = findViewById(R.id.tvTotalPrice);


        rcCart.setLayoutManager(new LinearLayoutManager(this));

        SharedPreferences sharedPreferences = getSharedPreferences(CART_PREFS, MODE_PRIVATE);
        // Gson gson = new Gson();
        String json = sharedPreferences.getString(CART_KEY, "[]");

        // שירותים
        authenticationService = FirebaseAuth.getInstance();
        databaseService = DatabaseService.getInstance();
        uid = authenticationService.getUid();


        // אתחול סל ריק בתחילה כדי למנוע NullPointer


        // טען את הסל מהמסד
        databaseService.getCart(uid, new DatabaseService.DatabaseCallback<Cart>() {
            @Override
            public void onCompleted(Cart resultCart) {
                if (resultCart != null && resultCart.getHairCuts() != null) {
                    cart = resultCart;

                    Toast.makeText(Shopping_basket.this, "" + cart.toString(), Toast.LENGTH_SHORT).show();
                } else cart = new Cart();

                cartAdapter = new CartAdapter(Shopping_basket.this, cart);
                rcCart.setAdapter(cartAdapter);
                cartAdapter.notifyDataSetChanged();
                tvprice.setText(cart.getTotalPrice() + "");

            }

            @Override
            public void onFailed(Exception e) {
                cart = new Cart();
                Log.e("error", e.getMessage());
                Toast.makeText(Shopping_basket.this, "שגיאה בטעינת הסל", Toast.LENGTH_SHORT).show();
            }
        });


        databaseService.getUser(uid, new DatabaseService.DatabaseCallback<User>() {
            @Override
            public void onCompleted(User theUser) {
                user = new User(theUser);
            }

            @Override
            public void onFailed(Exception e) {
                return;
            }
        });


        //adapter = new ItemsAdapter(cart.getItems());
        //rcCart.setAdapter(adapter);
    }


    public void beyomdPayment(View view) {
        //   Intent intent = new Intent(Shopping_basket.this, Payment.class);
//        intent.putExtra("total",cart.getTotalPrice());
//        startActivity(intent);
    }


    private void processOrder() {
        if (cart == null || cart.getHairCuts().isEmpty()) {
            Toast.makeText(this, "העגלה ריקה!", Toast.LENGTH_SHORT).show();
            return;
        }

        String orderId = databaseService.generateOrderId();
        Order order = new Order(orderId, cart.getHairCuts(), cart.getTotalPrice(), "new", user, 0);

        order.setTimestamp(System.currentTimeMillis());
        databaseService.createNewOreder(order, new DatabaseService.DatabaseCallback<Void>() {
            @Override
            public void onCompleted(Void object) {
                Toast.makeText(Shopping_basket.this, "הזמנה נשמרה!", Toast.LENGTH_SHORT).show();
                cart = new Cart();

                goUpdateCart(cart);
                


            }

            @Override
            public void onFailed(Exception e) {
                Toast.makeText(Shopping_basket.this, "שגיאה בשמירת ההזמנה", Toast.LENGTH_SHORT).show();

            }
        });


    }


    public void goUpdateCart(Cart cart) {
        databaseService.updateCart(cart, uid, new DatabaseService.DatabaseCallback<Void>() {
            @Override
            public void onCompleted(Void object) {

            }

            @Override
            public void onFailed(Exception e) {

            }
        });


    }

    @Override
    public void onClick(View view) {
        processOrder();
//        Intent go=new Intent(Shopping_basket.this,Payment.class);
//        go.putExtra("total",cart.getTotalPrice());
//        startActivity(go);
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

            startActivity(new Intent(
                    Shopping_basket.this,
                    HairCutsListActivity.class));
        }

        else if (id == R.id.menu_add_haircut) {

            startActivity(new Intent(
                    Shopping_basket.this,
                    AddHairCut.class));
        }

        else if (id == R.id.menu_profile) {

            startActivity(new Intent(
                    Shopping_basket.this,
                    UserProfileActivity.class));
        }

        else if (id == R.id.menu_orders) {

            startActivity(new Intent(
                    Shopping_basket.this,
                    UserOrders.class));
        }

        else if (id == R.id.menu_basket) {

            startActivity(new Intent(
                    Shopping_basket.this,
                    Shopping_basket.class));
        }

        return true;
    }
}
