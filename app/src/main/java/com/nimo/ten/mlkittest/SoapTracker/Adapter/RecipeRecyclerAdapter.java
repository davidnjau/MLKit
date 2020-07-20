package com.nimo.ten.mlkittest.SoapTracker.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.nimo.ten.mlkittest.R;
import com.nimo.ten.mlkittest.SoapTracker.Activities.PreviewRecipeDetails;
import com.nimo.ten.mlkittest.SoapTracker.Database.DatabaseHelper;
import com.nimo.ten.mlkittest.SoapTracker.HelperClass.ShowCustomToast;
import com.nimo.ten.mlkittest.SoapTracker.Pojo.RecipeDetailsPojo;

import java.util.ArrayList;


public class RecipeRecyclerAdapter extends RecyclerView.Adapter<RecipeRecyclerAdapter.ViewHolder>{

    private SharedPreferences preferences;

    private Context context;
    private ArrayList<RecipeDetailsPojo> IngredientsPojoArrayList;

    private DatabaseHelper databaseHelper;


    public RecipeRecyclerAdapter(Context context, ArrayList<RecipeDetailsPojo> IngredientsPojoArrayList) {
        this.context = context;
        this.IngredientsPojoArrayList = IngredientsPojoArrayList;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_recipes, parent, false);
        RecipeRecyclerAdapter.ViewHolder holder = new RecipeRecyclerAdapter.ViewHolder(view);

        preferences = context.getSharedPreferences("Soap", Context.MODE_PRIVATE);

        databaseHelper = new DatabaseHelper(context);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final RecipeRecyclerAdapter.ViewHolder holder, final int position) {

        String EssentialRatio = IngredientsPojoArrayList.get(position).getEssentialRatio();
        String SupperFat = IngredientsPojoArrayList.get(position).getSuperFat();
        String DateIn = IngredientsPojoArrayList.get(position).getDate_in();
        String LyeRatio = IngredientsPojoArrayList.get(position).getLyeRatio();
        String LiquidRatio = IngredientsPojoArrayList.get(position).getLiquid();

        String RecipeName = IngredientsPojoArrayList.get(position).getRecipeName();
        String LiquidWeight = IngredientsPojoArrayList.get(position).getLiquidWeight();
        String LyeWeight = IngredientsPojoArrayList.get(position).getLyeWeight();
        String EssentialOil = IngredientsPojoArrayList.get(position).getEssentialOil();
        String TotalWeight = IngredientsPojoArrayList.get(position).getTotalWeight();

        String recipeId = IngredientsPojoArrayList.get(position).getId();

        holder.tvRecipeName.setText(RecipeName);
        holder.tvDateIn.setText(DateIn);

        holder.itemView.setOnClickListener(view -> {

            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("recipe_id", recipeId);
            editor.apply();

            context.startActivity(new Intent(context, PreviewRecipeDetails.class));

        });

        holder.itemView.setOnLongClickListener(v -> {

            String txtId = IngredientsPojoArrayList.get(position).getId();
            databaseHelper.deleteNotes(Integer.parseInt(txtId));

            new ShowCustomToast((Activity)context, "Deleted " );

            IngredientsPojoArrayList.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, IngredientsPojoArrayList.size());


            return true;
        });

    }


    @Override
    public int getItemCount() {
        return IngredientsPojoArrayList.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvRecipeName, tvDateIn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvRecipeName = itemView.findViewById(R.id.tvRecipeName);
            tvDateIn = itemView.findViewById(R.id.tvDateIn);

        }
    }


}


