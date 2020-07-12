package com.nimo.ten.mlkittest.SoapTracker.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.nimo.ten.mlkittest.R;
import com.nimo.ten.mlkittest.SoapTracker.Database.DatabaseHelper;
import com.nimo.ten.mlkittest.SoapTracker.Pojo.CheckboxPojo;
import com.nimo.ten.mlkittest.SoapTracker.Pojo.CheckboxPojo;

import java.util.ArrayList;
import java.util.List;


public class SoapOilsAdapter extends RecyclerView.Adapter<SoapOilsAdapter.ViewHolder>{


    private Context context;
    public static ArrayList<CheckboxPojo> soapTrackerPojoArrayList;
//    private SparseBooleanArray itemStateArray = new SparseBooleanArray();
//    private List<String> mySelectedList = new ArrayList<>();
    
    public SoapOilsAdapter(Context context, ArrayList<CheckboxPojo> soapTrackerPojoArrayList) {
        this.context = context;
        this.soapTrackerPojoArrayList = soapTrackerPojoArrayList;

//        mySelectedList.clear();

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.oils_list_choose, parent, false);
        SoapOilsAdapter.ViewHolder holder = new SoapOilsAdapter.ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final SoapOilsAdapter.ViewHolder holder, final int position) {

//        holder.bind(position);

//        holder.checkBox.setText("Checkbox " + position);
        holder.checkBox.setText( soapTrackerPojoArrayList.get(position).getLiquids());
        holder.checkBox.setChecked(soapTrackerPojoArrayList.get(position).getSelected());

        holder.checkBox.setTag(position);
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Integer pos = (Integer) holder.checkBox.getTag();

                if (soapTrackerPojoArrayList.get(pos).getSelected()) {
                    soapTrackerPojoArrayList.get(pos).setSelected(false);
                } else {
                    soapTrackerPojoArrayList.get(pos).setSelected(true);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return soapTrackerPojoArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvOilName;
        CheckBox checkBox;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvOilName = itemView.findViewById(R.id.tvOilName);
            checkBox = itemView.findViewById(R.id.checkbox);

//            itemView.setOnClickListener(this);

        }



//        void bind(int position) {
//            // use the sparse boolean array to check
//            if (!itemStateArray.get(position, false)) {
//                checkbox.setChecked(false);}
//            else {
//                checkbox.setChecked(true);
//            }
//
//            tvOilName.setText(soapTrackerPojoArrayList.get(position).getLiquids());
////            checkbox.setText(soapTrackerPojoArrayList.get(position).getLiquids());
//
//        }

//        @Override
//        public void onClick(View v) {
//            int adapterPosition = getAdapterPosition();
//            if (!itemStateArray.get(adapterPosition, false)) {
//
//                checkbox.setChecked(true);
//                itemStateArray.put(adapterPosition, true);
//
//                mySelectedList.add(tvOilName.getText().toString());
//            }
//            else  {
//                checkbox.setChecked(false);
//                itemStateArray.put(adapterPosition, false);
//
//                mySelectedList.remove(tvOilName.getText().toString());
//            }
//
//        }
    }

//    public List<String> getList(){
//
//        return mySelectedList;
//    }

}


