package com.dragon_mandarin;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;

public class OCRActivity extends AppCompatActivity {

    private TextView ocrResult;
    private Button ocrGalleryButton;
    private Button ocrCameraButton;
    private TessOCR mTessOCR;
    private ImageView ocrImage;

    public static final int GALLERY_REQUEST = 1998;
    public static final int CAMERA_REQUEST = 1995;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ocr);

        ocrResult = findViewById(R.id.ocrResult);
        ocrImage = findViewById(R.id.ocrImage);
        ocrGalleryButton = findViewById(R.id.ocrGalleryButton);
        ocrGalleryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");
                startActivityForResult(photoPickerIntent, GALLERY_REQUEST);
            }
        });
        ocrCameraButton = findViewById(R.id.ocrCameraButton);
        ocrCameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, CAMERA_REQUEST);
                }
            }
        });
        mTessOCR = new TessOCR (getAssets());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK)
            switch (requestCode){
                case GALLERY_REQUEST:
                    Uri selectedImage = data.getData();
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
                        ocrImage.setImageBitmap(bitmap);
                        doOCR(bitmap);
                    } catch (IOException e) {
                        Log.i("TAG", "Some exception " + e);
                    }
                    break;
                case CAMERA_REQUEST:
                    Bundle extras = data.getExtras();
                    Bitmap imageBitmap = (Bitmap) extras.get("data");
                    ocrImage.setImageBitmap(imageBitmap);
                    doOCR(imageBitmap);
            }
    }

    private void doOCR (final Bitmap bitmap) {
        new Thread(new Runnable() {
            public void run() {
                final String srcText = mTessOCR.getOCRResult(bitmap);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        if (srcText != null && !srcText.equals("")) {
                            ocrResult.setText(srcText);
                        }
                        mTessOCR.onDestroy();
                    }
                });
            }
        }).start();
    }
}
