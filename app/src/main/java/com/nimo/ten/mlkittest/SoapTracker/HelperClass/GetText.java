package com.nimo.ten.mlkittest.SoapTracker.HelperClass;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.nimo.ten.mlkittest.R;

public class GetText {

    private DatabaseReference databaseReference;
    private FirebaseAuth auth;

    public String getText(TextView textView){

        String txtString = null;
        txtString = textView.getText().toString();

        return txtString;

    }

    public String getEditText(EditText editText){

        String txtString = null;
        txtString = editText.getText().toString();

        return txtString;

    }

    public void Visibility(EditText editText, TextView textView, ImageButton imageButton, String txtChild){

        if (editText.getVisibility() == View.GONE && textView.getVisibility() == View.VISIBLE){

            editText.setVisibility(View.VISIBLE);
            textView.setVisibility(View.GONE);

            editText.setText(getText(textView));

            imageButton.setBackgroundResource(R.drawable.icon_icon_done);

        }


    }

    public void SetDatabaseData(EditText editText, TextView textView, ImageButton imageButton, String txtChild){

        auth = FirebaseAuth.getInstance();
        String txtUid = auth.getCurrentUser().getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("user_details").child(txtUid);

        if (editText.getVisibility() == View.VISIBLE && textView.getVisibility() == View.GONE){

            databaseReference.child(txtChild).setValue(getEditText(editText));

            if (editText.getVisibility() == View.VISIBLE && textView.getVisibility() == View.GONE){

                editText.setVisibility(View.GONE);
                textView.setVisibility(View.VISIBLE);

                textView.setText(getEditText(editText));

                if (txtChild.equals("username"))imageButton.setBackgroundResource(R.drawable.icon_person_name);
                if (txtChild.equals("email"))imageButton.setBackgroundResource(R.drawable.icon_person_email);
                if (txtChild.equals("phone_number"))imageButton.setBackgroundResource(R.drawable.icon_person_phone);



            }

        }

    }




}
