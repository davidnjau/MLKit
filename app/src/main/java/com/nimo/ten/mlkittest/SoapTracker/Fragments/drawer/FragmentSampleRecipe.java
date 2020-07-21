package com.nimo.ten.mlkittest.SoapTracker.Fragments.drawer;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nimo.ten.mlkittest.R;
import com.nimo.ten.mlkittest.SoapTracker.Adapter.FirebaseRecipeRecyclerAdapter;
import com.nimo.ten.mlkittest.SoapTracker.Adapter.RecipeRecyclerAdapter;
import com.nimo.ten.mlkittest.SoapTracker.HelperClass.FirebaseData;
import com.nimo.ten.mlkittest.SoapTracker.Pojo.RecipeDetailsPojo;

import java.util.ArrayList;
import java.util.Objects;

import static com.nimo.ten.mlkittest.SoapTracker.HelperClass.UtilKt.replaceFragmenty;


public class FragmentSampleRecipe extends Fragment {

    public FragmentSampleRecipe(){

    }
    RecyclerView recyclerView;
    private DatabaseReference mDatabase;
    FirebaseRecipeRecyclerAdapter recipeRecyclerAdapter;


    private FloatingActionButton fab;
    final ArrayList<FirebaseData> userPojoArrayList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_sample_recipe, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mDatabase = FirebaseDatabase.getInstance().getReference().child("Recipes");


        fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(v -> {

            FragmentTransaction ft = requireActivity().getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.mainContent, new FragmentAdminRecipes());
            ft.addToBackStack(null);
            ft.commit();


        });

        getData();

        return view;
    }

    private void getData(){

        //Fetch Data from Firebase Database

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot childSnapshot : dataSnapshot.getChildren()){

                    FirebaseData firebase_data = new FirebaseData();

                    String recipe_name = childSnapshot.child("weight_information").child("recipe_name").getValue().toString();
                    String key = childSnapshot.getKey();

                    firebase_data.setRecipe_name(recipe_name);
                    firebase_data.setRecipe_key(key);

                    userPojoArrayList.add(firebase_data);

                    PopulateRecyclerView();

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private void PopulateRecyclerView() {

        recipeRecyclerAdapter = new FirebaseRecipeRecyclerAdapter(getActivity(), userPojoArrayList);
        recyclerView.setAdapter(recipeRecyclerAdapter);
    }


    @Override
    public void onStart() {
        super.onStart();

    }

}
