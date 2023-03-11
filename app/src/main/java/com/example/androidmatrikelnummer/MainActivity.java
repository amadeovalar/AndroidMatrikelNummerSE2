package com.example.androidmatrikelnummer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {

    Button submitButton;
    EditText matrikelNummerInput;
    Integer matrNr;
    TextView messageFromServer;

    String serverName = "se2-isys.aau.at";
    Integer portNumber = 53212;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Get the text from the user
        matrikelNummerInput = findViewById(R.id.MatrikelNummerInput);
//        Identify the button
        submitButton = findViewById(R.id.submit_button);
//        Identify the text message where the server message will be displayed
        messageFromServer = findViewById(R.id.serverMessage);


//
//        Create a click lister
//        submitButton.setOnClickListener(view -> Log.v("EditText", matrikelNummerInput.getText().toString()));

    }

    public void onClickSubmit(View view) {

        matrNr = Integer.parseInt(matrikelNummerInput.getText().toString());

//        Initiate a new thread
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Socket socket = new Socket(serverName, portNumber);
                    Log.v("Connected", socket.getRemoteSocketAddress().toString());

//                    sending the MatNr. to the server
//                    DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
//                    dataOutputStream.writeByte(matrNr);

                    PrintWriter toServer = new PrintWriter(socket.getOutputStream(), true);
                    toServer.println(matrNr);


//                    Log.v("DataOutput", dataOutputStream.toString());


//                    Reading the the server incomming message
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    String serverMessage = bufferedReader.readLine();

                    Log.v("EditText", matrikelNummerInput.getText().toString());
                    Log.v("ServerMessage", serverMessage);

//                    returning back to the UI thread from worker thread
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
//                            show the message in the textview
                            messageFromServer.setText(serverMessage);
                        }
                    });

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();

    }

}

