package com.idodrori.mygame.screens;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.idodrori.mygame.R;
import com.idodrori.mygame.adapters.HairCutAdapter;
import com.idodrori.mygame.modle.HairCut;
import com.idodrori.mygame.services.DatabaseService;
import java.util.List;

public class HairCutsListActivity extends AppCompatActivity {
    private RecyclerView rvHairCuts;
    private HairCutAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hair_cuts_list);

        rvHairCuts = findViewById(R.id.rvHairCuts);
        rvHairCuts.setLayoutManager(new LinearLayoutManager(this));

        DatabaseService databaseService = DatabaseService.getInstance();

        // משיכת הנתונים מהמסד
        databaseService.getHairCutList(new DatabaseService.DatabaseCallback<List<HairCut>>() {
            @Override
            public void onCompleted(List<HairCut> hairCuts) {
                if (hairCuts != null && !hairCuts.isEmpty()) {
                    adapter = new HairCutAdapter(hairCuts);
                    rvHairCuts.setAdapter(adapter);
                } else {
                    Toast.makeText(HairCutsListActivity.this, "לא נמצאו תספורות ברשימה", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailed(Exception e) {
                // הוספת הודעת שגיאה למשתמש
                Toast.makeText(HairCutsListActivity.this, "שגיאה בטעינת הנתונים: " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}