package com.idodrori.mygame.screens;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.idodrori.mygame.R;


public class UserProfileActivity extends AppCompatActivity implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_user_profile);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


    }

    @Override
    public void onClick(View view) {

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