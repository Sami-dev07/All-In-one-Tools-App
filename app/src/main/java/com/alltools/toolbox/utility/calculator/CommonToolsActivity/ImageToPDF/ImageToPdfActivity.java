package com.alltools.toolbox.utility.calculator.CommonToolsActivity.ImageToPDF;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.alltools.toolbox.utility.calculator.R;
import com.kerols.pdfconverter.CallBacks;
import com.kerols.pdfconverter.ImageToPdf;
import com.kerols.pdfconverter.PdfImageSetting;
import com.kerols.pdfconverter.PdfPage;

import java.io.File;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class ImageToPdfActivity extends AppCompatActivity {
    private ImageToPdf imageToPdf;
    String formattedDateTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_to_pdf);


        PdfPage pdfPage = new PdfPage(getApplicationContext());
        pdfPage.setPageSize(1000, 1000);

        PdfImageSetting mPdfImageSetting = new PdfImageSetting();
        mPdfImageSetting.setImageSize(800, 650);
        mPdfImageSetting.setMargin(100, 100, 250, 250);
        pdfPage.add(mPdfImageSetting);
        imageToPdf = new ImageToPdf(pdfPage, getApplicationContext());

        Date currentDate = new Date();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-HH-mm");
        formattedDateTime = formatter.format(currentDate);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            LocalDateTime currentDateTime = LocalDateTime.now();
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-HH-mm");
            formattedDateTime = currentDateTime.format(dateTimeFormatter);
        }

        findViewById(R.id.button).setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
            intent.setType("image/*");
            startActivityForResult(intent, 1);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 1) {
            assert data != null;
            imageToPdf.DataToPDF(data,
                    new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS),
                            "all tools" + formattedDateTime + ".pdf"), new CallBacks() {
                        @Override
                        public void onFinish(String path) {
                            Toast.makeText(getApplicationContext(), "onFinish", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onError(Throwable throwable) {
                            Toast.makeText(getApplicationContext(), "onError", Toast.LENGTH_SHORT).show();
                            Log.e("TAG", "onError: ", throwable);
                        }

                        @Override
                        public void onProgress(int progress, int max) {
                            Log.e("TAG", "onProgress: " + progress + "  " + max);
                        }

                        @Override
                        public void onCancel() {
                            Toast.makeText(getApplicationContext(), "onCancel", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onStart() {
                            Toast.makeText(getApplicationContext(), "onStart", Toast.LENGTH_SHORT).show();
                        }
                    });

        }
    }

}