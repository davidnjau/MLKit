package com.nimo.ten.mlkittest.SoapTracker.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.nimo.ten.mlkittest.R;
import com.nimo.ten.mlkittest.SoapTracker.Database.DatabaseHelperNew;
import com.nimo.ten.mlkittest.SoapTracker.HelperClass.Calculator;
import com.nimo.ten.mlkittest.SoapTracker.HelperClass.ShowCustomToast;
import com.nimo.ten.mlkittest.SoapTracker.Pojo.IngredientsPojo;

import java.util.ArrayList;


public class PreviewRecyclerAdapter extends RecyclerView.Adapter<PreviewRecyclerAdapter.ViewHolder>{

    private SharedPreferences preferences;

    private Context context;
    private ArrayList<IngredientsPojo> IngredientsPojoArrayList;

    private DatabaseHelperNew databaseHelper1;
    private boolean isGood = true;


    public PreviewRecyclerAdapter(Context context, ArrayList<IngredientsPojo> IngredientsPojoArrayList) {
        this.context = context;
        this.IngredientsPojoArrayList = IngredientsPojoArrayList;

    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_preview, parent, false);
        PreviewRecyclerAdapter.ViewHolder holder = new PreviewRecyclerAdapter.ViewHolder(view);

        preferences = context.getSharedPreferences("Soap", Context.MODE_PRIVATE);
        databaseHelper1 = new DatabaseHelperNew(context);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final PreviewRecyclerAdapter.ViewHolder holder, final int position) {

        ViewHolder viewHolder = (ViewHolder) holder;

        final int rowPos = viewHolder.getAdapterPosition();

        if (rowPos == 0){

            holder.tvNumber.setBackgroundResource(R.drawable.table_header_cell_bg);
            holder.tvIngredients.setBackgroundResource(R.drawable.table_header_cell_bg);
            holder.tvGrams.setBackgroundResource(R.drawable.table_header_cell_bg);

            holder.tvNumber.setText("#");
            holder.tvIngredients.setText("Ingredients");
            holder.tvGrams.setText("grams");

            holder.tvNumber.setTextColor(Color.parseColor("#F5FFFFFF"));
            holder.tvIngredients.setTextColor(Color.parseColor("#F5FFFFFF"));
            holder.tvGrams.setTextColor(Color.parseColor("#F5FFFFFF"));

        }else {

            holder.tvNumber.setBackgroundResource(R.drawable.table_content_cell_bg);
            holder.tvIngredients.setBackgroundResource(R.drawable.table_content_cell_bg);
            holder.tvGrams.setBackgroundResource(R.drawable.table_content_cell_bg);

            String txtIngredients = IngredientsPojoArrayList.get(rowPos - 1).getIngredient_name();
            if (txtIngredients.length() > 15){

                holder.tvIngredients.setText(txtIngredients.substring(0, 12) + "... ");


            }else {

                holder.tvIngredients.setText(txtIngredients);

            }

            holder.tvGrams.setText(IngredientsPojoArrayList.get(rowPos - 1).getGrams());

            holder.tvNumber.setText(String.valueOf(rowPos));

        }



    }

    @Override
    public int getItemCount() {
        return IngredientsPojoArrayList.size() + 1;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvNumber, tvGrams, tvIngredients;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvNumber = itemView.findViewById(R.id.tvNumber);
            tvGrams = itemView.findViewById(R.id.tvGrams);
            tvIngredients = itemView.findViewById(R.id.tvIngredients);

        }
    }


}


