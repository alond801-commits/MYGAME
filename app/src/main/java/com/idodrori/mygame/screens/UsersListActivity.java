package com.idodrori.mygame.screens;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.idodrori.mygame.R;
import com.idodrori.mygame.adapters.UserAdapter;
import com.idodrori.mygame.modle.User;
import com.idodrori.mygame.services.DatabaseService;

import java.util.ArrayList;
import java.util.List;

public class UsersListActivity extends AppCompatActivity {

    private static final String TAG = "UsersListActivity";

    private DatabaseService databaseService;
    private UserAdapter userAdapter;
    private TextView tvUserCount;
    private RecyclerView usersList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_users_list);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        databaseService = DatabaseService.getInstance();

        usersList = findViewById(R.id.rv_users_list);
        tvUserCount = findViewById(R.id.tv_user_count);

        if (usersList == null || tvUserCount == null) {
            Log.e(TAG, "RecyclerView or TextView is null - check XML IDs!");
            return;
        }

        usersList.setLayoutManager(new LinearLayoutManager(this));

        userAdapter = new UserAdapter(new UserAdapter.OnUserClickListener() {
            @Override
            public void onUserClick(User user) {
                Log.d(TAG, "User clicked: " + user);

                Intent intent = new Intent(UsersListActivity.this, UserProfileActivity.class);
                intent.putExtra("USER_UID", user.getId());
                startActivity(intent);
            }

            @Override
            public void onLongUserClick(User user) {
                Log.d(TAG, "User long clicked: " + user);
            }
        });

        usersList.setAdapter(userAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();

        databaseService.getUserList(new DatabaseService.DatabaseCallback<List<User>>() {

            @Override
            public void onCompleted(List<User> users) {

                if (users == null) {
                    users = new ArrayList<>();
                }

                if (userAdapter != null) {
                    userAdapter.setUserList(users);
                }

                if (tvUserCount != null) {
                    tvUserCount.setText("Total users: " + users.size());
                }

                Log.d(TAG, "Users loaded: " + users.size());
            }

            @Override
            public void onFailed(Exception e) {
                Log.e(TAG, "Failed to get users list", e);

                if (tvUserCount != null) {
                    tvUserCount.setText("Failed to load users");
                }
            }
        });
    }
}