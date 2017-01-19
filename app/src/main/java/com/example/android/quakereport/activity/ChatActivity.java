package com.example.android.quakereport.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.android.quakereport.R;
import com.example.android.quakereport.model.ChatModel;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ChatActivity extends AppCompatActivity {

    private ImageButton mBtn_send;
    private EditText mChat_text;

    //firebase
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mMessageDatabaseReference;
    private ChildEventListener mChilEventListener;

    private String mUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        mBtn_send = (ImageButton) findViewById(R.id.send_chat);
        mChat_text = (EditText) findViewById(R.id.ed_chat);

        //initialize firebase
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mMessageDatabaseReference = mFirebaseDatabase.getReference().child("messages");

        mBtn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChatModel chatModel = new ChatModel(
                        mChat_text.getText().toString(),
                        mUsername,
                        null);

                mMessageDatabaseReference.push().setValue(chatModel);

                // Clear input box
                mChat_text.setText("");
            }
        });

    }
}
