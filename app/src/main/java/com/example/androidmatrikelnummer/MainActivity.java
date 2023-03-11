package com.example.androidmatrikelnummer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    Button submitButton;
    EditText matrikelNummerInput;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Get the text from the user
        matrikelNummerInput = findViewById(R.id.MatrikelNummerInput);
//
//        Identify the button
        submitButton = findViewById(R.id.submit_button);
//
//        Create a click lister
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v("EditText", matrikelNummerInput.getText().toString());
            }
        });


    }
}