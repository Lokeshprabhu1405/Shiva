package com.example.mark9;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class secondactivity extends AppCompatActivity {
    TextToSpeech textToSpeech;
    Button Capture,check,submit,Update;
    private static int RESULT_LOAD_IMAGE = 1;
    public static final int CAM_REQUEST=1888;
    Bitmap b1,b2;
    EditText e;
    ImageView i,i1;
    Matrix matrix;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second);

        /*textToSpeech=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    textToSpeech.setLanguage(Locale.getDefault());
                }
            }
        });*/

        e=(EditText)findViewById(R.id.editText);
        Update=(Button)findViewById(R.id.bupdate);
        Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                String cdate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
                File sdcard=new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/iRAM");
                sdcard.mkdirs();
                File file=new File(sdcard,cdate+".txt");

                try
                {
                    String text;
                    //BufferedWriter for performance, true to set append to file flag
                    BufferedWriter buf = new BufferedWriter(new FileWriter(file, true));
                    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm dd/MM/yyyy");
                    String currentDateandTime = sdf.format(new Date());
                    Date date=new Date();
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(date);
                    int hours = cal.get(Calendar.HOUR_OF_DAY);
                    int mins = cal.get(Calendar.MINUTE);
                    if(hours>=8&&mins>40) {
                        text = "Roll NO:"+e.getText().toString()+"   ->   Late   " + currentDateandTime;
                        buf.append(text);
                        buf.newLine();
                        buf.close();
                        Toast.makeText(getApplicationContext(),"Updated successfully",Toast.LENGTH_SHORT).show();
                    }
                    else if(hours<=8&&mins<=40)
                    {
                        text = "Roll NO:"+e.getText().toString()+"->Present " + currentDateandTime;
                        buf.append(text);
                        buf.newLine();
                        buf.close();
                    }
                }
                catch (IOException e)
                {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                //Update.setText("hehe");
            }
        });
        submit=(Button)findViewById(R.id.button3);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try
                {
                    String text = e.getText().toString();
                    int i = Integer.parseInt(text);
                    int imageRes[] ={R.drawable.blue, R.drawable.brown,R.drawable.githu};
                    i1=(ImageView)findViewById(R.id.imageView2);
                    b1= BitmapFactory.decodeResource(getResources(), imageRes[i]);
                    b1=test(b1);
                    i1.setImageBitmap(b1);
                }
                catch(NumberFormatException nfe)
                {
                    nfe.printStackTrace();
                }

            }
        });
        Capture=(Button)findViewById(R.id.button);
        Capture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, CAM_REQUEST);
                }
            }
        });

        check=(Button)findViewById(R.id.bcheck);
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int c = 0;

                try {
                    for (int y = 0; y < b1.getHeight(); y++) {
                        for (int x = 0; x < b1.getWidth(); x++) {
                            if (b1.getPixel(x, y) == b2.getPixel(x, y)) {
                                c++;
                            }
                        }

                    }
                    if(c<3500)
                    {
                        check.setText("invalid");
                        //textToSpeech.speak("iris doesnt MATCH", TextToSpeech.QUEUE_FLUSH, null);
                    }
                    else
                    {
                        // String w=Integer.toString(c);
                        check.setText("marked");
                        //           textToSpeech.speak("Your attendance has been marked. Have a nice day", TextToSpeech.QUEUE_FLUSH, null);

                    }

                } catch (NullPointerException npe) {
                    npe.printStackTrace();
                }
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if (requestCode == CAM_REQUEST && resultCode == RESULT_OK) {
            try {
                Bundle extras = data.getExtras();
                b2 = (Bitmap) extras.get("data");
                i = (ImageView) findViewById(R.id.imageView);
                // matrix.postRotate(90);
                b2=Bitmap.createScaledBitmap(b2,975,501,false);
                // b2=Bitmap.createBitmap(b2, 0, 0, b2.getWidth(), b2.getHeight(), matrix, false);
                b2=test(b2);
                i.setImageBitmap(b2);
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

    /*public void onPause(){
        if(textToSpeech !=null){
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        super.onPause();
    }*/

}
