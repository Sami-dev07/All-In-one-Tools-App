package com.alltools.toolbox.utility.calculator.CommonToolsActivity.ImageCompressor;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.alltools.toolbox.utility.calculator.R;
import com.nguyenhoanglam.imagepicker.model.Config;
import com.nguyenhoanglam.imagepicker.model.Image;
import com.nguyenhoanglam.imagepicker.ui.imagepicker.ImagePicker;

import java.util.ArrayList;

public class ImageResizerActivity extends AppCompatActivity {
    private ArrayList<Image> images;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_resizer);

        findViewById(R.id.singleImage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                singleImagePicker();
            }
        });
        findViewById(R.id.multipleImage).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                multipleImagePicker();
            }
        });
    }

    public void singleImagePicker() {
        ImagePicker.with(this).setToolbarColor("#282A3B").setStatusBarColor("#6F7A92").setToolbarTextColor("#FFFFFF").setToolbarIconColor("#FFFFFF").setProgressBarColor("#ff5d3b").setBackgroundColor("#ffffff").setMultipleMode(true).setFolderMode(true).setShowCamera(false).setFolderTitle("Choose Image").setLimitMessage("Select only 1 photo").setMaxSize(1).setSavePath("Photo Compressor").setAlwaysShowDoneButton(false).setRequestCode(100).setKeepScreenOn(true).start();
    }

    public void multipleImagePicker() {
        ImagePicker.with(this).setToolbarColor("#282A3B").setStatusBarColor("#6F7A92").setToolbarTextColor("#FFFFFF").setToolbarIconColor("#FFFFFF").setProgressBarColor("#ff5d3b").setBackgroundColor("#ffffff").setMultipleMode(true).setFolderMode(true).setShowCamera(false).setFolderTitle("Choose Images").setImageTitle("Galleries").setDoneTitle("Done").setLimitMessage("You have reached selection limit").setMaxSize(10).setSavePath("Image Resizer").setAlwaysShowDoneButton(false).setRequestCode(100).setKeepScreenOn(true).start();
    }

    @Override
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i == 100 && i2 == -1 && intent != null) {
            this.images = intent.getParcelableArrayListExtra(Config.EXTRA_IMAGES);
            Intent intent2 = new Intent(this, ImageCompressorActivity.class);
            intent2.putParcelableArrayListExtra("selectedimages", this.images);
            startActivity(intent2);
        }
    }
}