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
import java.lang.reflect.Array;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.stream.IntStream;

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

        Log.v("Empty", String.valueOf(matrikelNummerInput.getText().toString().isEmpty()));


//
//        Create a click lister
//        submitButton.setOnClickListener(view -> Log.v("EditText", matrikelNummerInput.getText().toString()));

    }

    public void onClickSubmit(View view) {

        if (matrikelNummerInput.getText().toString().isEmpty()) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
//                            turn text to visible
                    messageFromServer.setVisibility(View.VISIBLE);
//                            show the message in the textview
                    messageFromServer.setText("Gib deine Matrikelnummer ein!");
                }
            });

        } else {
            submitButton.setEnabled(true);
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
//                            turn text to visible
                                messageFromServer.setVisibility(View.VISIBLE);
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

    public void onClickBerechnen(View view){
//        Get the user input text
        String matrikelNummerString = matrikelNummerInput.getText().toString();

        if (matrikelNummerString.isEmpty()) {


        } else {

//            Create a character array
            ArrayList<Character> chars = new ArrayList<>();
//            Add the individual characters to the ArrayList
            for (char c : matrikelNummerString.toCharArray()){
                chars.add(c);
            }

//            Sort the characters
            Collections.sort(chars);

//            Convert the chars to integers (for calculating if there are primes in the list)
            ArrayList<Integer> numbers = new ArrayList<>();
            for (char c : chars){
                numbers.add(Character.getNumericValue(c));
            }

//            Filter the prime numbers
            ArrayList<Integer> Primes = new ArrayList<>();
            for (int number : numbers){
                if (isPrime(number)){
                    Primes.add(number);
                }
            }
            numbers.removeAll(Primes);

//            Making a single number out of the array
            StringBuilder sb = new StringBuilder();
            for (int number : numbers){
                sb.append(number);
            }
            String concatNumber = sb.toString();


//            Display the sorted array
//            turn text to visible
            messageFromServer.setVisibility(View.VISIBLE);
//                            show the message in the textview
            messageFromServer.setText(concatNumber);

        }


    }

    public static boolean isPrime(int n){
        if (n <= 1){
            return false;
        }
        for (int i = 2; i <= Math.sqrt(n); i++){
            if(n % i == 0){
                return false;
            }
        }
        return true;
    }

}

