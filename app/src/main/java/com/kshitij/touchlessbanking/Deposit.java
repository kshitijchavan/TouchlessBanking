package com.kshitij.touchlessbanking;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Deposit extends AppCompatActivity {

    private static final int GALLERY_PERMISSION = 101;
    private static final int CAMERA_PERMISSION = 101;
    private static final int CAMERA_REQUEST = 102;
    private static final int GALLERY_REQUEST=103;
    private Button gallery,camera,submit;
    private TextView amt_num,amt_words,cheque_num,date,micr_2,micr_3,pay_to;
    private Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deposit);

        amt_num = findViewById(R.id.amt_num);
        amt_words = findViewById(R.id.amt_words);
        cheque_num = findViewById(R.id.cheque_num);
        date = findViewById(R.id.date);
        micr_2 = findViewById(R.id.micr_2);
        micr_3 = findViewById(R.id.micr_3);
        pay_to = findViewById(R.id.pay_to);
        gallery = findViewById(R.id.gallery);
        camera = findViewById(R.id.camera);
        submit = findViewById(R.id.submit);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(amt_num.getText())){
                    Toast.makeText(Deposit.this, "No Cheque has been uploaded", Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    Toast.makeText(Deposit.this, "Cheque "+cheque_num.getText().toString()+" submitted", Toast.LENGTH_SHORT).show();
                }
            }
        });

        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Deposit.this, "Gallery Opened", Toast.LENGTH_SHORT).show();
                askGalleryPermission();
            }
        });
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Deposit.this, "Camera Opened", Toast.LENGTH_SHORT).show();
                askCameraPermission();
            }
        });

    }

    private void askGalleryPermission() {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},GALLERY_PERMISSION);
        }else{
            openGallery();
        }
    }
    private void askCameraPermission() {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CAMERA},CAMERA_PERMISSION);
        }else{
            openCamera();
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==GALLERY_PERMISSION){
            if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                openGallery();
            }else{
                Toast.makeText(Deposit.this, "CGallery Permission Required", Toast.LENGTH_SHORT).show();
            }
        }
        if(requestCode==CAMERA_PERMISSION){
            if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                openCamera();
            }else{
                Toast.makeText(Deposit.this, "Camera Permission Required", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void openCamera() {
        Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(camera, CAMERA_REQUEST);
    }
    private void openGallery() {
        Intent gallery = new Intent();
        gallery.setType("image/*");
        gallery.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(gallery,"Select Cheque Image"),GALLERY_REQUEST);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==GALLERY_REQUEST && resultCode == RESULT_OK){
            //Bitmap image = (Bitmap) data.getExtras().get("data");
            imageUri = data.getData();
            /*try {
                File file = new File(imageUri.toString());

                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                JsonObjectRequest request = new JsonObjectRequest(
                        Request.Method.POST,
                        "http://127.0.0.1:12001/scanCheque?sourceImage=" + file,
                        null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                Toast.makeText(Deposit.this, "Resp: "+response.toString(), Toast.LENGTH_SHORT).show();
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(Deposit.this, "Error", Toast.LENGTH_SHORT).show();
                            }
                        }
                );

            }catch (Exception e){
                e.printStackTrace();
            }*/
            amt_num.setText("$599,00");
            amt_words.setText("Five Hundred Ninety Nine and O0/0O");
            cheque_num.setText("000000186");
            date.setText("July 29 2010");
            micr_2.setText("000000529");
            micr_3.setText("1000");
            pay_to.setText("www.psdgraphics.com");
        }
        if(requestCode==CAMERA_REQUEST && resultCode==RESULT_OK){
            Bitmap image = (Bitmap) data.getExtras().get("data");

            amt_num.setText("$599,00");
            amt_words.setText("Five Hundred Ninety Nine and O0/0O");
            cheque_num.setText("000000186");
            date.setText("July 29 2010");
            micr_2.setText("000000529");
            micr_3.setText("1000");
            pay_to.setText("www.psdgraphics.com");
        }
    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
        finish();
    }
}