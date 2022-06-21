package com.example.reviewsapp.activites;

import static com.example.reviewsapp.database.DatabaseHelper.REVIEWSTABLE;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.appsearch.SearchResults;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.example.reviewsapp.R;
import com.example.reviewsapp.adapters.ReviewAdapter;
import com.example.reviewsapp.database.DatabaseHelper;
import com.example.reviewsapp.model.CafeModel;
import com.example.reviewsapp.model.ReviewsModel;

import java.util.ArrayList;

public class Reviews extends AppCompatActivity {

    DatabaseHelper database;
    SQLiteDatabase sqLiteDatabase;
    RecyclerView recyclerView;
    ReviewAdapter reviewAdapter;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviews);

        database = new DatabaseHelper(this);

        recyclerView = findViewById(R.id.recycleReview);
        searchView = findViewById(R.id.searchView);
        searchView.clearFocus();
        displayData();
        recyclerView.setLayoutManager(new LinearLayoutManager(this, recyclerView.VERTICAL, false));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return true;
            }
        });
    }

    private void filterList(String text) {
        ArrayList<ReviewsModel> model = new ArrayList<>();
        for (ReviewsModel Kmodel : reviewAdapter.modelArrayList){
            if(Kmodel.getEstname().toLowerCase().contains(text.toLowerCase())){
                model.add(Kmodel);
            }
        }
        reviewAdapter.filterList(model);
    }

    private void displayData() {
        sqLiteDatabase = database.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from " + REVIEWSTABLE + "", null);
        ArrayList<ReviewsModel> model = new ArrayList<>();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String type = cursor.getString(2);
            String food = cursor.getString(3);
            String location = cursor.getString(4);
            String remarks = cursor.getString(5);

            model.add(new ReviewsModel(id, name, type, food, location, remarks));
        }
        cursor.close();
        reviewAdapter = new ReviewAdapter(this, R.layout.one_item_review, model, sqLiteDatabase);
        recyclerView.setAdapter(reviewAdapter);
    }
}