package com.example.reviewsapp.adapters;

import static com.example.reviewsapp.database.DatabaseHelper.REVIEWSTABLE;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.reviewsapp.R;
import com.example.reviewsapp.activites.Reviews;
import com.example.reviewsapp.database.DatabaseHelper;
import com.example.reviewsapp.model.ReviewsModel;

import java.util.ArrayList;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {

    Context context;
    int one_item_review;
    public ArrayList<ReviewsModel> modelArrayList;
    SQLiteDatabase sqLiteDatabase;

    public ReviewAdapter(Context context, int one_item_review, ArrayList<ReviewsModel> modelArrayList, SQLiteDatabase sqLiteDatabase) {
        this.context = context;
        this.one_item_review = one_item_review;
        this.modelArrayList = modelArrayList;
        this.sqLiteDatabase = sqLiteDatabase;
    }

    @NonNull
    @Override
    public ReviewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.one_item_review, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        final ReviewsModel model = modelArrayList.get(position);
        holder.name.setText("Id:  "+model.getEstid() + "\nName:  "+model.getEstname() + "\nType: "+model.getEsttype() + "\nFood served:  "+model.getFood()
                +"\nLocation:  "+model.getLocation() + "\nRemarks:  "+model.getRemarks());

        holder.menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(context, holder.menu);
                popupMenu.inflate(R.menu.flow_menu);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()){
                            case R.id.edit_menu:
                                ///////
                                //edit operation

                                Bundle bundle = new Bundle();
                                bundle.putInt("id", model.getEstid());
                                bundle.putString("name", model.getEstname());
                                bundle.putString("type", model.getEsttype());
                                bundle.putString("food", model.getFood());
                                bundle.putString("location", model.getLocation());
                                bundle.putString("remarks", model.getRemarks());

                                Intent intent = new Intent(context, Reviews.class);
                                intent.putExtra("userData", bundle);
                                context.startActivity(intent);
                                break;
                            case R.id.delete_menu:
                                //////
                                //delete operation
                                DatabaseHelper database = new DatabaseHelper(context);
                                sqLiteDatabase = database.getReadableDatabase();
                                long delete = sqLiteDatabase.delete(REVIEWSTABLE, "id=" +model.getEstid(), null);
                                if (delete != -1 ){
                                    Toast.makeText(context, "Record deleted", Toast.LENGTH_SHORT).show();

                                    //remove position after delete
                                    modelArrayList.remove(position);

                                    //update data
                                    notifyDataSetChanged();
                                }
                                break;
                            default:
                                return false;
                        }
                        return false;
                    }
                });
                //display menu
                popupMenu.show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return modelArrayList.size();
    }

    public void filterList(ArrayList<ReviewsModel> model) {
        modelArrayList = model;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        ImageButton menu;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = (TextView) itemView.findViewById(R.id.txt_review);
            menu = (ImageButton) itemView.findViewById(R.id.flowreview);
        }
    }
}
