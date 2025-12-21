package com.alltools.toolbox.utility.calculator.EssentialTools.Magnifier;

import android.net.Uri;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.alltools.toolbox.utility.calculator.R;
import com.github.chrisbanes.photoview.PhotoView;

public class ImageDisplayActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_display);
        String imagePath = getIntent().getStringExtra("imageUri");
        PhotoView photoView = (PhotoView) findViewById(R.id.photo_view);

        if (imagePath != null) {
            Uri imageUri = Uri.parse(imagePath);
            photoView.setImageURI(imageUri);
        }
    }
}