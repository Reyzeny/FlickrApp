package com.reyzeny.br;

import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

public class FullPhotoDisplay extends AppCompatActivity {
    public static final String IMAGE_URL = "image_url";
    String imageUrl;
    ImageView fullImage, close;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.full_image_display);
        imageUrl = getIntent().getStringExtra(IMAGE_URL);
        fullImage = findViewById(R.id.image_picture);
        close = findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        showImage();
    }

    private void showImage() {
        ViewTreeObserver viewTreeObserver = fullImage.getViewTreeObserver();
        viewTreeObserver.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                fullImage.getViewTreeObserver().removeOnPreDrawListener(this);
                Picasso.get().load(imageUrl).resize(fullImage.getMeasuredWidth(), fullImage.getMeasuredHeight()).centerInside().into(fullImage);
                return true;
            }
        });
    }
}
