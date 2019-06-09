package com.example.mark9;

import android.content.Intent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    Button b,b1;
    TextToSpeech textToSpeech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        b=(Button)findViewById(R.id.button);
        b1=(Button)findViewById(R.id.button2);
        textToSpeech=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    textToSpeech.setLanguage(Locale.getDefault());
                    textToSpeech.speak("Hello", TextToSpeech.QUEUE_FLUSH, null);
                }
            }
        });
    }
    public void launch(View View)
    {
        String button_text;
        button_text =((Button)View).getText().toString();
        if(button_text.equals("START"))
        {
            Intent ganesh = new Intent(this,secondactivity.class);
            startActivity(ganesh);
        }
        else if (button_text.equals("MANAGE"))
        {
            Intent mass = new Intent(this,Manage.class);
            startActivity(mass);

        }
    }
}
