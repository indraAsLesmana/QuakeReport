package com.example.android.quakereport.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.android.quakereport.R;
import com.example.android.quakereport.adapter.ChatAdapter;
import com.example.android.quakereport.model.ChatModel;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    private ImageButton mBtn_send;
    private EditText mChat_text;
    private ChatAdapter mChatAdapter;
    private ListView mMessageListview;

    //firebase
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mMessageDatabaseReference;
    private ChildEventListener mChilEventListener;

    public static String mUSERNAME;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        mBtn_send = (ImageButton) findViewById(R.id.send_chat);
        mChat_text = (EditText) findViewById(R.id.ed_chat);
        mMessageListview = (ListView) findViewById(R.id.list_chat);

        //initialize firebase
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mMessageDatabaseReference = mFirebaseDatabase.getReference().child("messages");

        //initialize adapter
        List<ChatModel> chatModels = new ArrayList<>();
        mChatAdapter = new ChatAdapter(this, R.layout.message_item, chatModels);
        mMessageListview.setAdapter(mChatAdapter);

        mBtn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChatModel chatModel = new ChatModel(
                        mChat_text.getText().toString(),
                        mUSERNAME,
                        null);

                mMessageDatabaseReference.push().setValue(chatModel);

                // Clear input box
                mChat_text.setText("");
            }
        });

        mChilEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) { //handle refresh every new data insert on child "Message"
                ChatModel chatModel = dataSnapshot.getValue(ChatModel.class);
                mChatAdapter.add(chatModel);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        mMessageDatabaseReference.addChildEventListener(mChilEventListener);
    }
}
