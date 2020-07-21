package com.nimo.ten.mlkittest.SoapTracker.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nimo.ten.mlkittest.R;
import com.nimo.ten.mlkittest.SoapTracker.Activities.FirebasePreviewRecipe;
import com.nimo.ten.mlkittest.SoapTracker.Activities.PreviewRecipeDetails;
import com.nimo.ten.mlkittest.SoapTracker.Database.DatabaseHelper;
import com.nimo.ten.mlkittest.SoapTracker.HelperClass.FirebaseData;
import com.nimo.ten.mlkittest.SoapTracker.HelperClass.ShowCustomToast;

import java.util.ArrayList;


public class FirebaseRecipeRecyclerAdapter extends RecyclerView.Adapter<FirebaseRecipeRecyclerAdapter.ViewHolder>{

    private SharedPreferences preferences;

    private Context context;
    private ArrayList<FirebaseData> IngredientsPojoArrayList;

    private DatabaseHelper databaseHelper;


    public FirebaseRecipeRecyclerAdapter(Context context, ArrayList<FirebaseData> IngredientsPojoArrayList) {
        this.context = context;
        this.IngredientsPojoArrayList = IngredientsPojoArrayList;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_recipes, parent, false);
        FirebaseRecipeRecyclerAdapter.ViewHolder holder = new FirebaseRecipeRecyclerAdapter.ViewHolder(view);

        preferences = context.getSharedPreferences("Soap", Context.MODE_PRIVATE);

        databaseHelper = new DatabaseHelper(context);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final FirebaseRecipeRecyclerAdapter.ViewHolder holder, final int position) {

//        String EssentialRatio = IngredientsPojoArrayList.get(position).getEssential_percentage();
//        String EssentialOil = IngredientsPojoArrayList.get(position).getEssential_weight();
//        String LiquidWeight = IngredientsPojoArrayList.get(position).getLiquid_weight();
//        String LyeWeight = IngredientsPojoArrayList.get(position).getNaoh_weight();
//        String OilAmount = IngredientsPojoArrayList.get(position).getOil_amount();
//        String SupperFat = IngredientsPojoArrayList.get(position).getSuper_fat_percentage();

        String recipekey = IngredientsPojoArrayList.get(position).getRecipe_key();
        String RecipeName = IngredientsPojoArrayList.get(position).getRecipe_name();

        holder.tvRecipeName.setText(RecipeName);

        holder.itemView.setOnClickListener(view -> {

            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("recipe_key", recipekey);
            editor.apply();

            context.startActivity(new Intent(context, FirebasePreviewRecipe.class));

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


