package com.idodrori.mygame.screens;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.idodrori.mygame.R;
import com.idodrori.mygame.modle.Cart;
import com.idodrori.mygame.modle.HairCut;
import com.idodrori.mygame.services.DatabaseService;
import com.idodrori.mygame.utils.ImageUtil;

public class HairCutProfileActivity extends AppCompatActivity {


    TextView tvPrice, tvType;
    ImageView ivImage;
    ImageView ivDog;
    TextView tvHairCutName;


    TextView tvSize;
    TextView tvDetails;
    HairCut currentHairCut;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private DatabaseService databaseService;
    private String hairCutId = null;
    private String userId;
    private Cart cart = new Cart();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_hair_cut_profile);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        initViews();

        databaseService = DatabaseService.getInstance();

        mAuth = FirebaseAuth.getInstance();
        userId = mAuth.getUid();


        databaseService.getCart(userId, new DatabaseService.DatabaseCallback<Cart>() {
            @Override
            public void onCompleted(Cart userCart) {

                if (userCart == null || userCart.getHairCuts() == null)
                    cart = new Cart();
                else cart = userCart;


            }

            @Override
            public void onFailed(Exception e) {
                cart = new Cart();
            }
        });

        hairCutId = getIntent().getStringExtra("HAIRCUT_UID");

        databaseService.getHairCut(hairCutId, new DatabaseService.DatabaseCallback<HairCut>() {
            @Override
            public void onCompleted(HairCut hairCut) {

                currentHairCut = hairCut;

                tvHairCutName.setText(currentHairCut.getName());
                tvPrice.setText(currentHairCut.getPrice() + "");
                tvSize.setText(currentHairCut.getSize());
                tvDetails.setText(currentHairCut.getDetails());
                tvType.setText(currentHairCut.getType());
                ivDog.setImageBitmap(ImageUtil.convertFrom64base(hairCut.getPic()));
            }

            @Override
            public void onFailed(Exception e) {

            }
        });


    }

    private void initViews() {
        mAuth = FirebaseAuth.getInstance();
        userId = mAuth.getUid();

        ivDog = findViewById(R.id.imageViewHairCut);
        tvHairCutName = findViewById(R.id.textViewHairCutName);
        tvPrice = findViewById(R.id.textViewHairCutPrice);
        tvType = findViewById(R.id.textViewHairCutType);
        tvSize = findViewById(R.id.textViewHairCutSize);
        tvDetails = findViewById(R.id.textViewHairCutDetails);

    }

    public void goBackToHaitCurList(View view) {

        Intent intent = new Intent(HairCutProfileActivity.this, HairCutsListActivity.class);
        startActivity(intent);
    }

    public void goAddCart(View view) {


        cart.addHairCut(currentHairCut);
        databaseService.updateCart(cart, userId, new DatabaseService.DatabaseCallback<Void>() {
            @Override
            public void onCompleted(Void object) {
                //toast


                Intent intent = new Intent(HairCutProfileActivity.this, Useractivity.class);
                startActivity(intent);

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