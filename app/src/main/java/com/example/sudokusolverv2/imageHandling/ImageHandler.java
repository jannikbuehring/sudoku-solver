package com.example.sudokusolverv2.imageHandling;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapRegionDecoder;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.sudokusolverv2.R;

import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Mat;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

public class ImageHandler extends AppCompatActivity {

    ImageView imageView;
    public static final int PICK_IMAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_view_image);

            OpenCVLoader.initDebug();

            imageView = (ImageView) findViewById(R.id.imageView);

            selectImage();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE) {
            try {

                imageView.setImageURI(data.getData());
                //processImage(data);
                //displayImage(img);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    Size size = new Size(450, 450);

    public void processImage(Intent data) {

        //Uri imageUri = data.getData();
        //System.out.println(imageUri.getPath());
        //Bitmap myBitmap = BitmapFactory.decodeFile(imageUri.getPath());


        //imageView.setImageBitmap(myBitmap);

        //Mat img = Imgcodecs.imread(imageUri.getPath());
        //return img;
            /*
            Mat resizedImg = new Mat();
            Imgproc.resize(img, resizedImg, size);
            Mat grayImg = new Mat();
            Imgproc.cvtColor(resizedImg, grayImg, Imgproc.COLOR_BGR2GRAY);
            Mat blurredImg = new Mat();
            Imgproc.GaussianBlur(grayImg, blurredImg, new Size(5,5), 1, 1);
            Mat imgThreshold = new Mat();
            Imgproc.adaptiveThreshold(blurredImg, imgThreshold, 255, 1, 1, 11, 2);
            imgThreshold.release();*/
    }

    public void displayImage(Mat mat) {
        Bitmap bitmap = Bitmap.createBitmap(mat.cols(), mat.rows(), Bitmap.Config.RGB_565);

        Utils.matToBitmap(mat, bitmap);

        imageView.setImageBitmap(bitmap);
    }

    public void selectImage() {
        Intent getIntent = new Intent(Intent.ACTION_GET_CONTENT);
        getIntent.setType("image/*");

        Intent pickIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        pickIntent.setType("image/*");

        Intent chooserIntent = Intent.createChooser(getIntent, "Select Image");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] {pickIntent});

        startActivityForResult(chooserIntent, PICK_IMAGE);
    }
}
