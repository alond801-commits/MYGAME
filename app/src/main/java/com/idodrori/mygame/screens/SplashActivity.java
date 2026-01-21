package com.idodrori.mygame.screens;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import com.idodrori.mygame.R;
import com.idodrori.mygame.modle.User;
import com.idodrori.mygame.services.DatabaseService;
import com.idodrori.mygame.utils.SharedPreferencesUtil;

public class SplashActivity extends AppCompatActivity {
    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        VideoView videoView = findViewById(R.id.splashVideoView);

        // הגדרת הנתיב לקובץ הווידאו בתיקיית raw
        String videoPath = "android.resource://" + getPackageName() + "/" + R.raw.splash_video;
        Uri uri = Uri.parse(videoPath);
        videoView.setVideoURI(uri);

        // מאזין לסיום הווידאו כדי לעבור למסך הבא
        videoView.setOnCompletionListener(mp -> {
            startNextActivity();
        });

        // במקרה של שגיאה בטעינה - נעבור מסך כדי לא "להתקוע" את המשתמש
        videoView.setOnErrorListener((mp, what, extra) -> {
            startNextActivity();
            return true;
        });

        videoView.start();
    }

    private void startNextActivity() {
        // 1. Check if the user is flagged as logged in
        if (SharedPreferencesUtil.isUserLoggedIn(this)) {

            // 2. Get the saved User ID (You'll need to add this method to your Util class)
            String userId = SharedPreferencesUtil.getUserId(this);

            if (userId != null) {
                DatabaseService.getInstance().getUser(userId, new DatabaseService.DatabaseCallback<User>() {
                    @Override
                    public void onCompleted(User user) {
                        if (user != null) {
                            navigateTo(MainActivity.class);
                        } else {
                            // User exists in Prefs but not in Database (e.g., deleted)
                            navigateTo(Landing.class);
                        }
                    }

                    @Override
                    public void onFailed(Exception e) {
                        navigateTo(Landing.class);
                    }
                });
            } else {
                // Logged in flag is true, but no ID found? Send to Landing.
                navigateTo(Landing.class);
            }
        } else {
            // 3. User is not logged in
            navigateTo(Landing.class);
        }
    }

    // Helper method to reduce code repetition
    private void navigateTo(Class<?> destination) {
        Intent intent = new Intent(SplashActivity.this, destination);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish(); // Ensure the splash screen is removed from the backstack
    }
}