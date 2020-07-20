package com.nimo.ten.mlkittest.SoapTracker.Fragments.drawer;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nimo.ten.mlkittest.R;
import com.nimo.ten.mlkittest.SoapTracker.Adapter.RecipeRecyclerAdapter;
import com.nimo.ten.mlkittest.SoapTracker.Adapter.SoapHealingRecyclerAdapter;
import com.nimo.ten.mlkittest.SoapTracker.Database.DatabaseHelper;
import com.nimo.ten.mlkittest.SoapTracker.Database.DatabaseHelperNew;
import com.nimo.ten.mlkittest.SoapTracker.Pojo.RecipeDetailsPojo;
import com.nimo.ten.mlkittest.SoapTracker.Pojo.SoapTrackerPojo;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;


public class FragmentMyRecipe extends Fragment {

    private String Soap_id;
    private DatabaseHelperNew databaseHelper;

    public FragmentMyRecipe(){

    }

    private RecyclerView recyclerView;
    private SharedPreferences preferences;
    private LinearLayout myview;
    RecyclerView.LayoutManager layoutManager;

    RecipeRecyclerAdapter recipeRecyclerAdapter;
    List<RecipeDetailsPojo> recipeDetailsPojoList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_my_recipe, container, false);
        preferences = getActivity().getSharedPreferences("Soap", MODE_PRIVATE);

        recyclerView = view.findViewById(R.id.recyclerView);
        myview = view.findViewById(R.id.myview);
        layoutManager = new LinearLayoutManager(getActivity());

        getActivity().setTitle("Soap recipes");

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        startRecylerView();


    }

    private void startRecylerView() {

        Soap_id = preferences.getString("soap_id", null);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        databaseHelper = new DatabaseHelperNew(getActivity());

        recipeDetailsPojoList = databaseHelper.getMyRecipes();
        recipeRecyclerAdapter = new RecipeRecyclerAdapter(getActivity(), (ArrayList<RecipeDetailsPojo>) recipeDetailsPojoList);
        recyclerView.setAdapter(recipeRecyclerAdapter);

        if (recipeRecyclerAdapter.getItemCount() == 0){

            myview.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);

        }else {

            myview.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);

        }

    }

}
