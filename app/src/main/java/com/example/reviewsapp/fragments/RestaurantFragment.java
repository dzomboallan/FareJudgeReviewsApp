package com.example.reviewsapp.fragments;

import static com.example.reviewsapp.database.DatabaseHelper.BARTABLE;
import static com.example.reviewsapp.database.DatabaseHelper.RESTAURANTTABLE;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.reviewsapp.R;
import com.example.reviewsapp.activites.Bar;
import com.example.reviewsapp.activites.MakeReview;
import com.example.reviewsapp.activites.Restaurant;
import com.example.reviewsapp.adapters.BarAdapter;
import com.example.reviewsapp.adapters.RestaurantAdapter;
import com.example.reviewsapp.database.DatabaseHelper;
import com.example.reviewsapp.model.BarModel;
import com.example.reviewsapp.model.RestaurantModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class RestaurantFragment extends Fragment {

    DatabaseHelper dBmain;
    SQLiteDatabase sqLiteDatabase;
    RecyclerView recyclerView;
    RestaurantAdapter resAdapter;
    FloatingActionButton resbtn;
    SearchView searchView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_restaurant, container, false);
        dBmain = new DatabaseHelper(getActivity());
        recyclerView = view.findViewById(R.id.recycleView3);
        resbtn = view.findViewById(R.id.res_btn);
        searchView = view.findViewById(R.id.searchView);
        searchView.clearFocus();
        displayData();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));

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
        resbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), Restaurant.class);
                startActivity(intent);

            }
        });

        return view;
    }

    private void displayData() {
        sqLiteDatabase = dBmain.getReadableDatabase();
        Cursor cursor =sqLiteDatabase.rawQuery("select * from "+RESTAURANTTABLE+"", null);
        ArrayList<RestaurantModel> resmodels = new ArrayList<>();
        while(cursor.moveToNext()){
            int id = cursor.getInt(0);
            byte[] avatar = cursor.getBlob(1);
            String name = cursor.getString(2);

            resmodels.add(new RestaurantModel(id, avatar, name));
        }
        cursor.close();
        resAdapter = new RestaurantAdapter(getContext(), R.layout.singledata, resmodels, sqLiteDatabase);
        recyclerView.setAdapter(resAdapter);
    }

    public void filterList(String  text){
        ArrayList<RestaurantModel> model = new ArrayList<>();
        for (RestaurantModel Rmodel : resAdapter.modelArrayList){
            if(Rmodel.getRestaurantname().toLowerCase().contains(text.toLowerCase())){
                model.add(Rmodel);
            }
        }
        resAdapter.filterList(model);
    }
}