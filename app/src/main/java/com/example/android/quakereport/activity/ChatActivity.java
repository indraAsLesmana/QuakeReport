package com.example.android.quakereport.activity;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.android.quakereport.R;
import com.example.android.quakereport.Services.ChatNotifService;
import com.example.android.quakereport.adapter.ChatAdapter;
import com.example.android.quakereport.callback.QbChatDialogMessageListenerImp;
import com.example.android.quakereport.helper.ChatRemiderTask;
import com.example.android.quakereport.helper.Constant;
import com.example.android.quakereport.helper.Notification;
import com.example.android.quakereport.model.ChatModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.quickblox.chat.QBChatService;
import com.quickblox.chat.model.QBChatDialog;
import com.quickblox.chat.model.QBChatMessage;

import org.jivesoftware.smack.ConnectionListener;
import org.jivesoftware.smack.chat.ChatMessageListener;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    private static final int RC_PHOTO_PICKER = 2001;
    private static final String TAG = ChatActivity.class.getSimpleName();
    private ImageButton mBtn_send;
    private EditText mChat_text;
    private ChatAdapter mChatAdapter;
    private ListView mMessageListview;
    private ImageButton mImageSelect;

    //quickblox
    private ConnectionListener chatConnectionListener;
    private QBChatDialog qbChatDialog;
    private ArrayList<QBChatMessage> unShownMessages;
    private ChatMessageListener chatMessageListener;

    public static final String EXTRA_DIALOG_ID = "dialogId";
    public static String mUSERNAME;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        mBtn_send = (ImageButton) findViewById(R.id.send_chat);
        mChat_text = (EditText) findViewById(R.id.ed_chat);
        mMessageListview = (ListView) findViewById(R.id.list_chat);
        mImageSelect = (ImageButton) findViewById(R.id.imageSelect);

        //initialize adapter
        List<ChatModel> chatModels = new ArrayList<>();
//        mChatAdapter = new ChatAdapter(this, R.layout.message_item, chatModels);
        mMessageListview.setAdapter(mChatAdapter);

        Log.v(TAG, "onCreate ChatActivity on Thread ID = " + Thread.currentThread().getId());
        qbChatDialog = (QBChatDialog) getIntent().getSerializableExtra(EXTRA_DIALOG_ID);
        qbChatDialog.initForChat(QBChatService.getInstance());
        chatMessageListener = new ChatMessageListener();
        qbChatDialog.addMessageListener(chatMessageListener);



        mImageSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setType(Constant.IMAGE_TYPE);
                intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                startActivityForResult(Intent.createChooser(intent, "Complete action using"), RC_PHOTO_PICKER);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RC_PHOTO_PICKER){
            if (resultCode == RESULT_OK){
                Toast.makeText(this, "Hold picture, for multiple select", Toast.LENGTH_SHORT).show();
            }
        }

    }

    public void showMessage(QBChatMessage message) {
        if (mChatAdapter != null) {
            mChatAdapter.add(message);
//            scrollMessageListDown();
        } else {
            if (unShownMessages == null) {
                unShownMessages = new ArrayList<>();
            }
            unShownMessages.add(message);
        }
    }

    public class ChatMessageListener extends QbChatDialogMessageListenerImp {
        @Override
        public void processMessage(String s, QBChatMessage qbChatMessage, Integer integer) {
            showMessage(qbChatMessage);
        }
    }

}
