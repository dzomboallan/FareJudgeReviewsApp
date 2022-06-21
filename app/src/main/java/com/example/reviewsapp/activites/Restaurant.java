package com.example.reviewsapp.activites;

import static com.example.reviewsapp.database.DatabaseHelper.BARTABLE;
import static com.example.reviewsapp.database.DatabaseHelper.RESTAURANTTABLE;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.reviewsapp.R;
import com.example.reviewsapp.database.DatabaseHelper;
import com.example.reviewsapp.fragments.RestaurantFragment;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.ByteArrayOutputStream;

public class Restaurant extends AppCompatActivity {

    DatabaseHelper dBmain;
    SQLiteDatabase sqLiteDatabase;
    ImageView avatar;
    EditText name;
    Button submit, display, edit;
    int id = 0;


    public static final int CAMERA_REQUEST = 100;
    public static final int STORAGE_REQUEST = 101;

    String [] cameraPermission;
    String [] storagePermission;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant);

        cameraPermission = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
        storagePermission = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};

        dBmain = new DatabaseHelper(this);
        findid();
        insertData();
        imagePick();
        editData();
    }

    private void editData() {
        if (getIntent().getBundleExtra("userdata") != null){
            Bundle bundle = getIntent().getBundleExtra("userdata");
            id = bundle.getInt("id");

            //for image
            byte[] bytes = bundle.getByteArray("avatar");
            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0 , bytes.length);
            avatar.setImageBitmap(bitmap);

            //for set name
            name.setText(bundle.getString("name"));

            //visible edit button and hide submit button

            submit.setVisibility(View.GONE);
            edit.setVisibility(View.VISIBLE);
        }
    }

    private void imagePick() {
        avatar.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View view) {
                int avatar = 0;
                if (avatar == 0){
                    if (!checkCameraPermission()){
                        requestCameraPermission();
                    }else{
                        pickFromGallery();
                    }
                }else if (avatar == 1){
                    if (!checkStoragePermission()){
                        requestStoragePermission();
                    }else{
                        pickFromGallery();
                    }
                }
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void requestStoragePermission() {
        requestPermissions(storagePermission, STORAGE_REQUEST);
    }

    private boolean checkStoragePermission() {
        boolean result = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)==(PackageManager.PERMISSION_GRANTED);
        return result;
    }

    private void pickFromGallery() {
        CropImage.activity().start(this);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void requestCameraPermission() {
        requestPermissions(cameraPermission, CAMERA_REQUEST);
    }

    private boolean checkCameraPermission() {
        boolean results = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)==(PackageManager.PERMISSION_GRANTED);
        boolean results2 = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)==(PackageManager.PERMISSION_GRANTED);
        return results && results2;
    }

    private void insertData() {
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentValues cv = new ContentValues();
                cv.put("avatar", ImageViewToByte(avatar));
                cv.put("name", name.getText().toString());
                sqLiteDatabase = dBmain.getWritableDatabase();
                Long recinsert = sqLiteDatabase.insert(RESTAURANTTABLE,null,cv);
                if (recinsert != null){
                    Toast.makeText(Restaurant.this, "Inserted successfully", Toast.LENGTH_SHORT).show();

                    //Clear when click submit

                    avatar.setImageResource(R.mipmap.ic_launcher);
                    name.setText("");
                }
            }
        });

        display.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                display.setVisibility(view.GONE);
                submit.setVisibility(view.GONE);
                name.setVisibility(view.GONE);
                avatar.setVisibility(view.GONE);
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.restaurantActivity, new RestaurantFragment()).commit();
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentValues cv = new ContentValues();
                cv.put("avatar", ImageViewToByte(avatar));
                cv.put("name", name.getText().toString());

                sqLiteDatabase = dBmain.getWritableDatabase();
                long recedit = sqLiteDatabase.update(RESTAURANTTABLE, cv, "id=" +id, null);
                if (recedit != -1){
                    Toast.makeText(Restaurant.this, "Update successful", Toast.LENGTH_SHORT).show();
                    //clear data after submit
                    avatar.setImageResource(R.mipmap.ic_launcher);
                    name.setText("");
                    //edit hide and submit visible

                    edit.setVisibility(View.GONE);
                    submit.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private byte[] ImageViewToByte(ImageView avatar) {

        Bitmap bitmap = ((BitmapDrawable) avatar.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, stream);
        byte[] bytes = stream.toByteArray();
        return bytes;
    }

    private void findid() {

        avatar = (ImageView) findViewById(R.id.avatar);
        name = (EditText) findViewById(R.id.edit_name);
        submit = (Button) findViewById(R.id.btn_submit);
        display = (Button) findViewById(R.id.btn_display);
        edit = (Button) findViewById(R.id.btn_edit);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode){
            case CAMERA_REQUEST: {
                if (grantResults.length > 0) {
                    boolean camera_accept = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean storage_accept = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    if (camera_accept && storage_accept) {
                        pickFromGallery();
                    } else {
                        Toast.makeText(this, "Enable Camera and Storage permission", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            break;
            case STORAGE_REQUEST:{
                if (grantResults.length > 0) {
                    boolean storage_accept = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if (storage_accept) {
                        pickFromGallery();
                    } else {
                        Toast.makeText(this, "Enable Storage permission", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK){
                Uri resultUri = result.getUri();
                Picasso.with(this).load(resultUri).into(avatar);
            }
        }
    }

}