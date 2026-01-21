package com.idodrori.mygame;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

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
        if (isFinishing()) return;
        Intent intent = new Intent(SplashActivity.this, Landing.class);
        startActivity(intent);
        finish();
    }
}