package com.example.android.quakereport.activity;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.android.quakereport.R;
import com.example.android.quakereport.adapter.ChatAdapter;
import com.example.android.quakereport.model.ChatModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    private static final int RC_PHOTO_PICKER = 2001;
    private ImageButton mBtn_send;
    private EditText mChat_text;
    private ChatAdapter mChatAdapter;
    private ListView mMessageListview;
    private ImageButton mImageSelect;

    //firebase
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mMessageDatabaseReference;
    private ChildEventListener mChilEventListener;
    private FirebaseStorage mFirebaseStorage;
    private StorageReference mStorageReference;

    public static String mUSERNAME;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        mBtn_send = (ImageButton) findViewById(R.id.send_chat);
        mChat_text = (EditText) findViewById(R.id.ed_chat);
        mMessageListview = (ListView) findViewById(R.id.list_chat);
        mImageSelect = (ImageButton) findViewById(R.id.imageSelect);

        //initialize firebase
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mMessageDatabaseReference = mFirebaseDatabase.getReference().child("messages");
        mFirebaseStorage = FirebaseStorage.getInstance();
        mStorageReference = mFirebaseStorage.getReference().child("chat_photos");

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

        mImageSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setType("image/jpeg");
                intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                startActivityForResult(Intent.createChooser(intent, "Complete action using"), RC_PHOTO_PICKER);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RC_PHOTO_PICKER){
            if (resultCode == RESULT_OK){
                Toast.makeText(this, "Hold picture, for multiple select", Toast.LENGTH_SHORT).show();
                uploadImage(data);
            }
        }

    }

    private void uploadImage(Intent data) {
        Uri seletedImage = data.getData();
        StorageReference photoRef =
                mStorageReference.child(seletedImage.getLastPathSegment());

        UploadTask result = photoRef.putFile(seletedImage);

        result.addOnSuccessListener(this,
        new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(ChatActivity.this, "upload success", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(ChatActivity.this, "Upload failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
