package com.spadhi.pixabaygallery.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.spadhi.pixabaygallery.Models.Image;
import com.spadhi.pixabaygallery.R;

public class ImageDetailsActivity extends AppCompatActivity {
    ImageView imageView,userImageView;
    TextView totalViewsTextView,userTextView;
    Image image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_details);
        image= (Image) getIntent().getSerializableExtra("image");
        imageView=findViewById(R.id.fullImageView);
        userImageView=findViewById(R.id.userImageView);
        totalViewsTextView=findViewById(R.id.totalViewTextView);
        userTextView=findViewById(R.id.userTextView);
        Glide.with(this).load(image.getImageURL()).into(imageView);
        Glide.with(this).load(image.getUserImageURL()).into(userImageView);
        userTextView.setText(image.getUser());
        totalViewsTextView.setText(String.valueOf(image.getViews()));
    }
}