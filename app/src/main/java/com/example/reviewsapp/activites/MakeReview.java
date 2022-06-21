package com.example.reviewsapp.activites;

import static com.example.reviewsapp.database.DatabaseHelper.REVIEWSTABLE;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.reviewsapp.R;
import com.example.reviewsapp.database.DatabaseHelper;
import com.google.android.material.textfield.TextInputEditText;

public class MakeReview extends AppCompatActivity implements View.OnClickListener{

    TextView txtHeader, txtDropdown, rateCount;
    TextInputEditText inputName, inputFood, inputLocation, inputRemarks;
    Button submit, edit, display;
    DatabaseHelper database;
    SQLiteDatabase sqLiteDatabase;
    RatingBar ratingBar;
    float rateValue;
    String temp;

    int id = 0;

    ArrayAdapter<String> arrayAdapter;
    String [] establishments;
    AutoCompleteTextView autoCompleteTextDropdown;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_review);

        database = new DatabaseHelper(this);

        findId();
        editText();
        establishments = getResources().getStringArray(R.array.Establishments);
        arrayAdapter = new ArrayAdapter(this, R.layout.dropdown_item, establishments);
        autoCompleteTextDropdown = findViewById(R.id.autoCompleteTextDropdown);
        autoCompleteTextDropdown.setAdapter(arrayAdapter);

        submit.setOnClickListener(this);
        edit.setOnClickListener(this);
        display.setOnClickListener(this);

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                rateValue = ratingBar.getRating();

                if (rateValue <=1 && rateValue > 0)
                    rateCount.setText("Bad " +rateValue+" /5");
                else if (rateValue <=2 && rateValue > 1)
                    rateCount.setText("Ok " +rateValue+" /5");
                else if (rateValue <=3 && rateValue > 2)
                    rateCount.setText("Good " +rateValue+" /5");
                else if (rateValue <=4 && rateValue > 3)
                    rateCount.setText("Very good " +rateValue+" /5");
                else if (rateValue <=5 && rateValue > 4)
                    rateCount.setText("Best " +rateValue+" /5");
            }
        });
    }

    private void editText() {
        if (getIntent().getBundleExtra("userData") != null){
            Bundle bundle = getIntent().getBundleExtra("userData");
            inputName.setText(bundle.getString("name"));
            txtDropdown.setText(bundle.getString("type"));
            inputFood.setText(bundle.getString("food"));
            inputLocation.setText(bundle.getString("location"));
            inputRemarks.setText(bundle.getString("remarks"));

            //visible edit button and hide submit button

            submit.setVisibility(View.GONE);
            edit.setVisibility(View.VISIBLE);
        }
    }

    private void findId() {
        txtHeader = (TextView) findViewById(R.id.txtHeader);
        txtDropdown =(TextView) findViewById(R.id.autoCompleteTextDropdown);
        inputName =(TextInputEditText) findViewById(R.id.textInputName);
        inputFood =(TextInputEditText) findViewById(R.id.textInputFood);
        inputLocation =(TextInputEditText) findViewById(R.id.textInputLocation);
        inputRemarks =(TextInputEditText) findViewById(R.id.textInputRemarks);
        submit = (Button) findViewById(R.id.submit);
        edit = (Button) findViewById(R.id.edit);
        display = (Button) findViewById(R.id.display);
        rateCount = findViewById(R.id.rateCount);
        ratingBar = findViewById(R.id.ratingBar);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.submit:{
                ContentValues cv = new ContentValues();
                cv.put("name", inputName.getText().toString());
                cv.put("type", txtDropdown.getText().toString());
                cv.put("food", inputFood.getText().toString());
                cv.put("location", inputLocation.getText().toString());
                cv.put("remarks", inputRemarks.getText().toString());
                sqLiteDatabase = database.getWritableDatabase();
                Long insert = sqLiteDatabase.insert(REVIEWSTABLE,null,cv);

                if (inputName.length() == 0){
                    inputName.setError("Error");
                }
                else if (txtDropdown.length() == 0){
                    txtDropdown.setError("Error");
                }else {

                    if (inputName.getText().toString().trim().isEmpty() || txtDropdown.getText().toString().trim().isEmpty()) {
                        Toast.makeText(MakeReview.this, "All fields marked required* are necessary", Toast.LENGTH_SHORT).show();
                    } else if (insert != null) {
                        Toast.makeText(MakeReview.this, "Inserted successfully", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(this, Reviews.class);
                        startActivity(i);
                    }
                }

                inputName.setText("");
                txtDropdown.setText("");
                inputFood.setText("");
                inputLocation.setText("");
                inputRemarks.setText("");

            }
            break;
            case R.id.display:{
                Intent intent = new Intent(this, Reviews.class);
                startActivity(intent);
            }
            break;
            case R.id.edit:{
                edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        ContentValues cv = new ContentValues();
                        cv.put("name", inputName.getText().toString());
                        cv.put("type", txtDropdown.getText().toString());
                        cv.put("food", inputFood.getText().toString());
                        cv.put("location", inputLocation.getText().toString());
                        cv.put("remarks", inputRemarks.getText().toString());
                        sqLiteDatabase = database.getWritableDatabase();
                        long recedit = sqLiteDatabase.update(REVIEWSTABLE, cv, "id=" +id, null);
                        if (recedit != -1){
                            Toast.makeText(MakeReview.this, "Update successful", Toast.LENGTH_SHORT).show();
                            //clear data after submit
                            inputName.setText("");
                            txtDropdown.setText("");
                            inputFood.setText("");
                            inputLocation.setText("");
                            inputRemarks.setText("");

                            //edit hide and submit visible

                            edit.setVisibility(View.GONE);
                            submit.setVisibility(View.VISIBLE);
                        }
                    }
                });
            }break;
            default:
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        }

    }
}