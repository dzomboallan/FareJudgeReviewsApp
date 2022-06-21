package com.example.reviewsapp.fragments;

import static com.example.reviewsapp.database.DatabaseHelper.BARTABLE;

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
import android.widget.Toast;

import com.example.reviewsapp.R;
import com.example.reviewsapp.activites.Bar;
import com.example.reviewsapp.activites.MakeReview;
import com.example.reviewsapp.adapters.BarAdapter;
import com.example.reviewsapp.database.DatabaseHelper;
import com.example.reviewsapp.model.BarModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class BarFragment extends Fragment {

    DatabaseHelper dBmain;
    SQLiteDatabase sqLiteDatabase;
    RecyclerView recyclerView;
    BarAdapter myAdapter;
    FloatingActionButton addbtn;
    SearchView searchView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_bar, container, false);
        dBmain = new DatabaseHelper(getActivity());
        recyclerView = view.findViewById(R.id.recycleView2);
        addbtn = view.findViewById(R.id.bar_btn);
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
        addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), Bar.class);
                startActivity(intent);

            }
        });

        return view;

    }

    private void displayData() {
        sqLiteDatabase = dBmain.getReadableDatabase();
        Cursor cursor =sqLiteDatabase.rawQuery("select * from "+BARTABLE+"", null);
        ArrayList<BarModel> models = new ArrayList<>();
        while(cursor.moveToNext()){
            int id = cursor.getInt(0);
            byte[] avatar = cursor.getBlob(1);
            String name = cursor.getString(2);

            models.add(new BarModel(id, avatar, name));
        }
        cursor.close();
        myAdapter = new BarAdapter(getContext(), R.layout.singledata, models, sqLiteDatabase);
        recyclerView.setAdapter(myAdapter);
    }

    public void filterList(String  text){
        ArrayList<BarModel> model = new ArrayList<>();
        for (BarModel Dmodel : myAdapter.modelArrayList){
            if(Dmodel.getBarname().toLowerCase().contains(text.toLowerCase())){
                model.add(Dmodel);
            }
        }
        myAdapter.filterList(model);
    }

}