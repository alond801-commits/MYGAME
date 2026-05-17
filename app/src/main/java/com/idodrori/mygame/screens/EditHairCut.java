package com.idodrori.mygame.screens;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.idodrori.mygame.R;
import com.idodrori.mygame.modle.HairCut;
import com.idodrori.mygame.services.DatabaseService;
import com.idodrori.mygame.utils.ImageUtil;

public class EditHairCut extends AppCompatActivity {


    /// Activity result launcher for capturing image from camera
    int SELECT_PICTURE = 200;
    HairCut currentHairCut;
    private EditText etIname, etIPrice, etISize, etIDetails;
    private Spinner spIType;
    private Button btnGallery, btnCamera, btnAddItem;
    private ImageView ivIPic;
    private DatabaseService databaseService;
    private ActivityResultLauncher<Intent> captureImageLauncher;
    private String hairCutId = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_hair_cut);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        InitViews();

        /// request permission for the camera and storage
        ImageUtil.requestPermission(this);

        /// get the instance of the database service
        databaseService = DatabaseService.getInstance();


        hairCutId = getIntent().getStringExtra("HAIRCUT_UID");


        databaseService.getHairCut(hairCutId, new DatabaseService.DatabaseCallback<HairCut>() {
            @Override
            public void onCompleted(HairCut hairCut) {

                currentHairCut = hairCut;

                etIname.setText(currentHairCut.getName());
                etIPrice.setText(currentHairCut.getPrice() + "");
                etISize.setText(currentHairCut.getSize());
                etIDetails.setText(currentHairCut.getDetails());
            }

            @Override
            public void onFailed(Exception e) {

            }
        });


        /// register the activity result launcher for capturing image from camera
        captureImageLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Bitmap bitmap = (Bitmap) result.getData().getExtras().get("data");
                        ivIPic.setImageBitmap(bitmap);
                    }
                });

        btnGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImageFromGallery();


            }
        });

        btnCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                captureImageFromCamera();

            }
        });

        btnAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String itemName = etIname.getText().toString();
                String itemDetails = etIDetails.getText().toString();
                String itemPrice = etIPrice.getText().toString();

                double price = Double.parseDouble(itemPrice);
                String itemType = "jhjhj";

                //spIType.getSelectedItem().toString()+"";
                String itemSize = etISize.getText().toString();


                String imagePic = ImageUtil.convertTo64Base(ivIPic);


                if (itemName.isEmpty() || itemDetails.isEmpty() ||
                        itemPrice.isEmpty() || itemType.isEmpty() || itemSize.isEmpty()) {
                    Toast.makeText(EditHairCut.this, "אנא מלא את כל השדות", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(EditHairCut.this, "המוצר נוסף בהצלחה!", Toast.LENGTH_SHORT).show();
                }

                /// generate a new id for the item

                currentHairCut.setPrice(price);
                currentHairCut.setName(itemName);
                currentHairCut.setPic(imagePic);


                /// save the item to the database and get the result in the callback
                databaseService.updateHairCut(currentHairCut, new DatabaseService.DatabaseCallback<Void>() {
                    @Override
                    public void onCompleted(Void object) {
                        Log.d("TAG", "Item update successfully");
                        Toast.makeText(EditHairCut.this, "Item update successfully", Toast.LENGTH_SHORT).show();
                        /// clear the input fields after adding the item for the next item
                        Log.d("TAG", "Clearing input fields");

                        Intent intent = new Intent(EditHairCut.this, AdminActivity.class);
                        startActivity(intent);


                    }

                    @Override
                    public void onFailed(Exception e) {
                        Log.e("TAG", "Failed to add item", e);
                        Toast.makeText(EditHairCut.this, "Failed to add food", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void InitViews() {
        etIname = findViewById(R.id.etNameEdit);
        etIPrice = findViewById(R.id.etPriceEdit);
        etISize = findViewById(R.id.etSizeEdit);
        etIDetails = findViewById(R.id.etDetailsEdit);
        spIType = findViewById(R.id.spTYpeEdit);
        btnGallery = findViewById(R.id.btnGalleryEdit);
        btnCamera = findViewById(R.id.btnCamareEdit);
        btnAddItem = findViewById(R.id.BtnAddNewHairCutEdit);
        ivIPic = findViewById(R.id.ivHairCutEdit);
    }

    /// select image from gallery
    private void selectImageFromGallery() {

        imageChooser();
    }

    /// capture image from camera
    private void captureImageFromCamera() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        captureImageLauncher.launch(takePictureIntent);
    }

    void imageChooser() {

        // create an instance of the
        // intent of the type image
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);

        // pass the constant to compare it
        // with the returned requestCode
        startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE);
    }

    // this function is triggered when user
    // selects the image from the imageChooser
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            // compare the resultCode with the
            // SELECT_PICTURE constant
            if (requestCode == SELECT_PICTURE) {
                // Get the url of the image from data
                Uri selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    // update the preview image in the layout
                    ivIPic.setImageURI(selectedImageUri);
                }
            }
        }
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
                    EditHairCut.this,
                    AddHairCut.class));
        }

        else if (id == R.id.menu_users) {

            startActivity(new Intent(
                    EditHairCut.this,
                    UsersListActivity.class));
        }

        else if (id == R.id.menu_haircuts) {

            startActivity(new Intent(
                    EditHairCut.this,
                    HairCutsListActivity.class));
        }

        else if (id == R.id.menu_orders) {

            startActivity(new Intent(
                    EditHairCut.this,
                    EditHairCut.class));
        }

        return true;
    }
}
