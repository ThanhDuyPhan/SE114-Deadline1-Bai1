package com.example.thanhduyphan.bai1;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {

    private static final int CAMERA_PIC_REQUEST = 0;
    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 1;
    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkPermission();
        image = findViewById(R.id.image);
        name = findViewById(R.id.editTextName);
        mail = findViewById(R.id.editTextMail);
        phone = findViewById(R.id.editTextPhone);
        readData();
    }

    public void takePic(View view) {
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_PIC_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_PIC_REQUEST) {
            if(resultCode== Activity.RESULT_OK) {
                Bitmap image1 = (Bitmap) data.getExtras().get("data");
                image.setImageBitmap(image1);
            }
        }
    }

    public void checkPermission()
    {
        if (ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,
                    Manifest.permission.CAMERA)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(MainActivity.this,
                        new String[]{Manifest.permission.CAMERA},
                        MY_PERMISSIONS_REQUEST_CAMERA);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            // Permission has already been granted
        }
    }

    public void cancel(View view) {
        finish();
    }

    EditText name,mail,phone;
    public void save(View view) {
        String data = name.getText().toString() + "\n" + mail.getText().toString() + "\n" + phone.getText().toString();
        try {
            FileOutputStream out = openFileOutput("data.txt",0);
            OutputStreamWriter writer = new OutputStreamWriter(out);
            writer.write(data);
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readData()
    {
        try {
            FileInputStream in = openFileInput("data.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            String data="" ;
            if ((data =reader.readLine())!= null) { name.setText(data);}
            if ((data =reader.readLine())!= null) { mail.setText(data);}
            if ((data =reader.readLine())!= null) { phone.setText(data);}
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
