package com.example.mark9;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;

public class Manage extends AppCompatActivity {
 Button cap,add;
 EditText studname;
 ImageView imv;
 Bitmap st;
    private static int RESULT_LOAD_IMAGE = 1;
    private static final int CAM_REQUEST=1888;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manageimgs
        );
        studname=(EditText)findViewById(R.id.editText2);
        cap=(Button)findViewById(R.id.button4);
        cap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePictureIntent1 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent1.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent1, CAM_REQUEST);
                }
            }
        });

        add=(Button)findViewById(R.id.button5);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/Database");
                dir.mkdirs();

                // Create a name for the saved image
                File file = new File(dir, studname.getText().toString()+".jpg");

                // Show a toast message on successful save
                Toast.makeText(getApplicationContext(), "Image Saved to SD Card", Toast.LENGTH_SHORT).show();
                try {

                    FileOutputStream output = new FileOutputStream(file);

                    // Compress into png format image from 0% - 100%
                    //st.compress(Bitmap.CompressFormat.PNG, 100, output);
                    st.compress(Bitmap.CompressFormat.JPEG,100 , output);
                    output.flush();
                    output.close();
                }

                catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        });
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data1){
        if (requestCode == CAM_REQUEST && resultCode == RESULT_OK) {
            try {
                Bundle extras1 = data1.getExtras();
                st = (Bitmap) extras1.get("data1");
                imv = (ImageView) findViewById(R.id.imageView3);
                // matrix.postRotate(90);
                st=Bitmap.createScaledBitmap(st,975,501,false);
                // b2=Bitmap.createBitmap(b2, 0, 0, b2.getWidth(), b2.getHeight(), matrix, false);
                st=test(st);
                imv.setImageBitmap(st);
                imv.setVisibility(View.VISIBLE);
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
    }
    public Bitmap test(Bitmap bmpOriginal)
    {
        int width, height;
        height = bmpOriginal.getHeight();
        width = bmpOriginal.getWidth();

        Bitmap bmpGrayscale = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bmpGrayscale);
        Paint paint = new Paint();
        ColorMatrix cm = new ColorMatrix();
        cm.setSaturation(0);
        ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm);
        paint.setColorFilter(f);
        c.drawBitmap(bmpOriginal, 0, 0, paint);
        return bmpGrayscale;
    }

}