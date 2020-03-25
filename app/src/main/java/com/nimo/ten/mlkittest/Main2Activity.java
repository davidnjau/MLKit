package com.nimo.ten.mlkittest;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.ml.common.FirebaseMLException;
import com.google.firebase.ml.common.modeldownload.FirebaseModelManager;
import com.google.firebase.ml.vision.FirebaseVision;
import com.google.firebase.ml.vision.automl.FirebaseAutoMLLocalModel;
import com.google.firebase.ml.vision.automl.FirebaseAutoMLRemoteModel;
import com.google.firebase.ml.vision.common.FirebaseVisionImage;
import com.google.firebase.ml.vision.label.FirebaseVisionImageLabel;
import com.google.firebase.ml.vision.label.FirebaseVisionImageLabeler;
import com.google.firebase.ml.vision.label.FirebaseVisionOnDeviceAutoMLImageLabelerOptions;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Main2Activity extends AppCompatActivity {

    private ImageButton imgBtn1;
    private ImageView imageView1;

    FirebaseVisionImageLabeler labeler;

    List<String> myPetName = new ArrayList<>();
    List<Float> myPetConfidence = new ArrayList<>();

    TextView tvName1, tvName2, tvConf1, tvConf2, tvConclussion, tvConclussion1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        imgBtn1 = findViewById(R.id.imgBtn1);
        imageView1 = findViewById(R.id.imageView1);

        tvName1 = findViewById(R.id.tvName1);
        tvName2 = findViewById(R.id.tvName2);
        tvConf1 = findViewById(R.id.tvConf1);
        tvConf2 = findViewById(R.id.tvConf2);

        tvConclussion = findViewById(R.id.tvConclussion);
        tvConclussion1 = findViewById(R.id.tvConclussion1);

        imgBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ImagePicker.Companion.with(Main2Activity.this)
                        .crop()
                        .compress(720)
                        .maxResultSize(720, 720)
                        .start();

            }
        });

        FirebaseAutoMLLocalModel localModel = new FirebaseAutoMLLocalModel.Builder()
                .setAssetFilePath("ml_kit/manifest.json")
                .build();


        try {
            FirebaseVisionOnDeviceAutoMLImageLabelerOptions options =
                    new FirebaseVisionOnDeviceAutoMLImageLabelerOptions.Builder(localModel)
                            .setConfidenceThreshold(0.0f)  // Evaluate your model in the Firebase console
                            // to determine an appropriate value.
                            .build();
            labeler = FirebaseVision.getInstance().getOnDeviceAutoMLImageLabeler(options);
        } catch (FirebaseMLException e) {
            // ...
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            myPetConfidence.clear();
            myPetName.clear();

            tvConf1.setText("Confidence interval");
            tvConf2.setText("Confidence interval");

            tvName1.setText("Animal name");
            tvName2.setText("Animal name");

            tvConclussion.setText("No animal yet");
            tvConclussion1.setVisibility(View.GONE);

            File fileImage = new File(Objects.requireNonNull(ImagePicker.Companion.getFilePath(data)));
            imageView1.setImageBitmap(BitmapFactory.decodeFile(fileImage.getAbsolutePath()));

            getImage(imageView1);

        } else if (resultCode == 64) {

            Toast.makeText(this, ImagePicker.Companion.getError(data), Toast.LENGTH_SHORT).show();

        } else {

            Toast.makeText(this, "Task Cancelled", Toast.LENGTH_SHORT).show();
        }

    }

    private void getImage(ImageView imageView) {

        if (imageView.getDrawable() != null) {

            Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();

            if (bitmap != null) {

                FirebaseVisionImage image = FirebaseVisionImage.fromBitmap(bitmap);

                labeler.processImage(image)
                        .addOnSuccessListener(new OnSuccessListener<List<FirebaseVisionImageLabel>>() {
                            @Override
                            public void onSuccess(List<FirebaseVisionImageLabel> labels) {
                                // Task completed successfully

                                for (FirebaseVisionImageLabel label: labels) {
                                    String text = label.getText();
                                    float confidence = label.getConfidence();

                                    myPetName.add(text);
                                    myPetConfidence.add(confidence);

                                }

                                AssignValues();

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Task failed with an exception
                                // ...
                            }
                        });


            }

        }

    }

    private void AssignValues() {

        String txtName = myPetName.get(0);
        float txtConf = myPetConfidence.get(0);

        String txtName1 = myPetName.get(1);
        float txtConf1 = myPetConfidence.get(1);

        tvName1.setText(txtName);
        tvConf1.setText(String.valueOf(txtConf));

        tvName2.setText(txtName1);
        tvConf2.setText(String.valueOf(txtConf1));

        if (txtConf > 0.700000 || txtConf1 > 0.70000) {

            if (txtConf > txtConf1) {

                tvConclussion.setText("The animal is a " + txtName);

            } else {

                tvConclussion.setText("The animal is a " + txtName1);

            }

        }else {

            tvConclussion1.setVisibility(View.VISIBLE);
            tvConclussion1.setText("This is neither a cat nor a dog");
        }

    }
}
