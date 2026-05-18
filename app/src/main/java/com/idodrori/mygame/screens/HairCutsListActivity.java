package com.idodrori.mygame.screens;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.idodrori.mygame.R;
import com.idodrori.mygame.adapters.HairCutAdapter;
import com.idodrori.mygame.modle.HairCut;
import com.idodrori.mygame.services.DatabaseService;

import java.util.ArrayList;
import java.util.List;

public class HairCutsListActivity extends AppCompatActivity {


    private static final String TAG = "HairCutsListActivity";
    ArrayList<HairCut> hairCutArrayList = new ArrayList<>();
    private RecyclerView rvHairCuts;
    private HairCutAdapter adapter;
    private DatabaseService databaseService;




        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_hair_cuts_list);

            rvHairCuts = findViewById(R.id.rvHairCuts);
            rvHairCuts.setLayoutManager(new LinearLayoutManager(this));

            databaseService = DatabaseService.getInstance();


            adapter = new HairCutAdapter(hairCutArrayList, new HairCutAdapter.OnHairCutClickListener() {
                @Override
                public void onHairCutClick(HairCut hairCut) {
                    Log.d(TAG, "HairCut clicked: " + hairCut);
                    if (!Login.isAdmin) {


                        Intent intent = new Intent(HairCutsListActivity.this, HairCutProfileActivity.class);
                        intent.putExtra("HAIRCUT_UID", hairCut.getId());
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(HairCutsListActivity.this, EditHairCut.class);
                        intent.putExtra("HAIRCUT_UID", hairCut.getId());
                        startActivity(intent);

                    }

                }

                @Override
                public void onLongHairCutClick(HairCut hairCut) {

                }
            });

            rvHairCuts.setAdapter(adapter);


            // משיכת הנתונים מהמסד
            databaseService.getHairCutList(new DatabaseService.DatabaseCallback<List<HairCut>>() {
                @Override
                public void onCompleted(List<HairCut> hairCutsList) {
                    if (hairCutsList != null && !hairCutsList.isEmpty()) {


                        hairCutArrayList.addAll(hairCutsList);
                        adapter.notifyDataSetChanged();


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