package com.example.reviewsapp.adapters;

import static com.example.reviewsapp.database.DatabaseHelper.BARTABLE;

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
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.reviewsapp.R;
import com.example.reviewsapp.activites.Establishments;
import com.example.reviewsapp.activites.MakeReview;
import com.example.reviewsapp.database.DatabaseHelper;
import com.example.reviewsapp.model.BarModel;

import java.util.ArrayList;

public class BarAdapter extends RecyclerView.Adapter<BarAdapter.ViewHolder>{

    Context context;
    int singledata;
    public ArrayList<BarModel> modelArrayList;
    SQLiteDatabase sqLiteDatabase;

    public BarAdapter(Context context, int singledata, ArrayList<BarModel> modelArrayList, SQLiteDatabase sqLiteDatabase) {
        this.context = context;
        this.singledata = singledata;
        this.modelArrayList = modelArrayList;
        this.sqLiteDatabase = sqLiteDatabase;
    }

    @NonNull
    @Override
    public BarAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.singledata, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BarAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        final BarModel model = modelArrayList.get(position);
        byte[] image = model.getProavatar();
        Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length);
        holder.imageavatar.setImageBitmap(bitmap);
        holder.txtname.setText(model.getBarname());

        holder.flowmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(context, holder.flowmenu);
                popupMenu.inflate(R.menu.flow_menu);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()){
                            case R.id.edit_menu:
                                ///////
                                //edit operation

                                Bundle bundle = new Bundle();
                                bundle.putInt("id", model.getId());
                                bundle.putByteArray("avatar", model.getProavatar());
                                bundle.putString("name", model.getBarname());

                                Intent intent = new Intent(context, Establishments.class);
                                intent.putExtra("userdata", bundle);
                                context.startActivity(intent);
                                break;
                            case R.id.delete_menu:
                                //////
                                //delete operation
                                DatabaseHelper dBmain = new DatabaseHelper(context);
                                sqLiteDatabase = dBmain.getReadableDatabase();
                                long recdelete = sqLiteDatabase.delete(BARTABLE, "id=" +model.getId(), null);
                                if (recdelete != -1 ){
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

        holder.imageavatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                Intent intent = new Intent(context, MakeReview.class);
                intent.putExtra("userdata", bundle);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return modelArrayList.size();
    }

    public void filterList(ArrayList<BarModel> model) {
            modelArrayList = model;
            notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageavatar;
        TextView txtname;
        ImageView flowmenu;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imageavatar = (ImageView) itemView.findViewById(R.id.viewavatar);
            txtname = (TextView) itemView.findViewById(R.id.txt_name);
            flowmenu = (ImageView) itemView.findViewById(R.id.flowmenu);
        }
    }
}
